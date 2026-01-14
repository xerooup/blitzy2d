package org.xeroup.blitzy.core.internal

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil
import org.xeroup.blitzy.core.Game
import org.xeroup.blitzy.core.Settings
import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.graphics.FrameBuffer
import org.xeroup.blitzy.graphics.internal.DrawContextImpl
import org.xeroup.blitzy.graphics.internal.TextureRenderer
import org.xeroup.blitzy.input.Keyboard
import org.xeroup.blitzy.input.Mouse

class Engine(private val settings: Settings, private val game: Game) {
    private var window: Long = 0
    private var lastFrameTime = 0L
    private var fpsCounter = 0
    private var fpsTimer = 0f
    private lateinit var frameBuffer: FrameBuffer
    private lateinit var drawContext: DrawContext
    private lateinit var textureRenderer: TextureRenderer

    companion object {
        private var currentInstance: Engine? = null

        fun forceStop() {
            currentInstance?.window?.let { window ->
                GLFW.glfwSetWindowShouldClose(window, true)
            }
        }
    }

    fun run() {
        currentInstance = this
        initWindow()
        initGL()
        initFrameBuffer()
        game.create()

        lastFrameTime = System.nanoTime()
        loop()
        cleanup()
        currentInstance = null
    }

    private fun initWindow() {
        if (!GLFW.glfwInit()) throw IllegalStateException("failed to initialize glfw")
        GLFW.glfwDefaultWindowHints()
        if (!settings.decorated) {
            GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE)
            println(GLFW.GLFW_FALSE)
        }
        window = GLFW.glfwCreateWindow(
            settings.width,
            settings.height,
            settings.title,
            MemoryUtil.NULL,
            MemoryUtil.NULL
        )
        if (settings.windowX != null && settings.windowY != null)
            GLFW.glfwSetWindowPos(window, settings.windowX!!, settings.windowY!!)
        IconLoader.setIcon(window, settings.iconPath)
        Keyboard.init(window)
        Mouse.init(window)
        if (window == MemoryUtil.NULL) throw RuntimeException("failed to create window")
        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(if (settings.targetFPS > 0) 1 else 0)
    }

    private fun initGL() {
        GL.createCapabilities()
        GL11.glClearColor(0f, 0f, 0f, 1f)
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_DEPTH_TEST)

        // projection
        GL11.glMatrixMode(GL11.GL_PROJECTION)
        GL11.glLoadIdentity()
        GL11.glOrtho(0.0, settings.width.toDouble(), settings.height.toDouble(), 0.0, -1.0, 1.0)
        GL11.glMatrixMode(GL11.GL_MODELVIEW)
        GL11.glLoadIdentity()

        // blending
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    }

    private fun initFrameBuffer() {
        frameBuffer = FrameBuffer(settings.width, settings.height)
        drawContext = DrawContextImpl(frameBuffer)
        textureRenderer = TextureRenderer(frameBuffer)
    }

    private fun loop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            val currentTime = System.nanoTime()
            val delta = (currentTime - lastFrameTime) / 1_000_000_000f
            lastFrameTime = currentTime

            fpsCounter++
            fpsTimer += delta
            Game.uptime += delta

            if (fpsTimer >= 1f) {
                Game.fps = fpsCounter
                fpsCounter = 0
                fpsTimer = 0f
            }

            game.update(delta)

            Keyboard.update()
            Mouse.update()

            // clear buffer and render game
            frameBuffer.clear(settings.background)
            game.render(drawContext)

            textureRenderer.updateTexture()
            textureRenderer.render()

            GLFW.glfwSwapBuffers(window)
            GLFW.glfwPollEvents()
        }
    }

    private fun cleanup() {
        textureRenderer.dispose()
        GLFW.glfwDestroyWindow(window)
        GLFW.glfwTerminate()
    }
}