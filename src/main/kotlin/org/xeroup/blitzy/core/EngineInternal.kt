package org.xeroup.blitzy.core

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil
import org.xeroup.blitzy.core.internal.IconLoader
import org.xeroup.blitzy.graphics.FrameBuffer
import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.graphics.internal.DrawContextImpl
import org.xeroup.blitzy.graphics.internal.TextureRenderer
import org.xeroup.blitzy.input.Input
import org.xeroup.blitzy.input.MouseInput
import java.lang.System.nanoTime

class EngineInternal(private val settings: Settings, private val game: Game) {
    private var window: Long = 0
    private var lastFrameTime = 0L
    private lateinit var frameBuffer: FrameBuffer
    private lateinit var drawContext: DrawContext
    private lateinit var textureRenderer: TextureRenderer

    companion object {
        private var currentInstance: EngineInternal? = null

        fun forceStop() {
            currentInstance?.window?.let { window ->
                glfwSetWindowShouldClose(window, true)
            }
        }
    }

    fun run() {
        currentInstance = this
        initWindow()
        initGL()
        initFrameBuffer()
        game.create()

        lastFrameTime = nanoTime()
        loop()
        cleanup()
        currentInstance = null
    }

    private fun initWindow() {
        if (!glfwInit()) throw IllegalStateException("failed to initialize glfw")
        glfwDefaultWindowHints()
        println(settings.decorated)
        if (!settings.decorated) {
            glfwWindowHint(GLFW_DECORATED, GLFW_FALSE)
            println(GLFW_FALSE)
        }
        window = glfwCreateWindow(
            settings.width,
            settings.height,
            settings.title,
            MemoryUtil.NULL,
            MemoryUtil.NULL
        )
        if (settings.windowX != null && settings.windowY != null)
            glfwSetWindowPos(window, settings.windowX!!, settings.windowY!!)
        IconLoader.setIcon(window, settings.iconPath)
        Input.init(window)
        MouseInput.init(window)
        if (window == MemoryUtil.NULL) throw RuntimeException("failed to create window")
        glfwMakeContextCurrent(window)
        glfwSwapInterval(if (settings.targetFPS > 0) 1 else 0)
    }

    private fun initGL() {
        GL.createCapabilities()
        glClearColor(0f, 0f, 0f, 1f)
        glClear(GL_COLOR_BUFFER_BIT)
        glEnable(GL_TEXTURE_2D)
        glDisable(GL_DEPTH_TEST)

        // projection
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        glOrtho(0.0, settings.width.toDouble(), settings.height.toDouble(), 0.0, -1.0, 1.0)
        glMatrixMode(GL_MODELVIEW)
        glLoadIdentity()

        // blending
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }

    private fun initFrameBuffer() {
        frameBuffer = FrameBuffer(settings.width, settings.height)
        drawContext = DrawContextImpl(frameBuffer)
        textureRenderer = TextureRenderer(frameBuffer)
    }

    private fun loop() {
        while (!glfwWindowShouldClose(window)) {
            val currentTime = nanoTime()
            val delta = (currentTime - lastFrameTime) / 1_000_000_000f
            lastFrameTime = currentTime

            game.update(delta)
            MouseInput.update()

            // clear buffer and render game
            frameBuffer.clear(settings.background)
            game.render(drawContext)

            textureRenderer.updateTexture()
            textureRenderer.render()

            glfwSwapBuffers(window)
            glfwPollEvents()
        }
    }

    private fun cleanup() {
        textureRenderer.dispose()
        glfwDestroyWindow(window)
        glfwTerminate()
    }
}