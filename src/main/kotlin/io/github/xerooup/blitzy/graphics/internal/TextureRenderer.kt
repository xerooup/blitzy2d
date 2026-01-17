package io.github.xerooup.blitzy.graphics.internal

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import io.github.xerooup.blitzy.graphics.FrameBuffer
import org.lwjgl.BufferUtils
import java.nio.ByteBuffer

class TextureRenderer(private val buffer: FrameBuffer) {
    private var textureId = 0

    init {
        createTexture()
    }

    private fun createTexture() {
        textureId = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, textureId)

        // pixel-art settings
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)

        // create texture
        glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGB,
            buffer.width, buffer.height, 0,
            GL_RGB, GL_UNSIGNED_BYTE, null as ByteBuffer?
        )
    }

    // update texture with current frame buffer pixels
    fun updateTexture() {
        val pixels = buffer.getPixels()
        val byteBuffer = BufferUtils.createByteBuffer(pixels.size)
        byteBuffer.put(pixels)
        byteBuffer.flip()

        glBindTexture(GL_TEXTURE_2D, textureId)
        glTexSubImage2D(
            GL_TEXTURE_2D, 0, 0, 0,
            buffer.width, buffer.height,
            GL_RGB, GL_UNSIGNED_BYTE, byteBuffer
        )
    }

    fun render() {
        glBindTexture(GL_TEXTURE_2D, textureId)
        glEnable(GL_TEXTURE_2D)
        glColor3f(1f, 1f, 1f)  // white color

        glBegin(GL_QUADS)
        glTexCoord2f(0f, 0f); glVertex2f(0f, 0f)
        glTexCoord2f(1f, 0f); glVertex2f(buffer.width.toFloat(), 0f)
        glTexCoord2f(1f, 1f); glVertex2f(buffer.width.toFloat(), buffer.height.toFloat())
        glTexCoord2f(0f, 1f); glVertex2f(0f, buffer.height.toFloat())
        glEnd()
    }

    // clean up texture
    fun dispose() {
        glDeleteTextures(textureId)
    }
}