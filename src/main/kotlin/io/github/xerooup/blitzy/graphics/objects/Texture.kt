package io.github.xerooup.blitzy.graphics.objects

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.BufferUtils
import javax.imageio.ImageIO

class Texture(var width: Int, var height: Int, var pixels: ByteArray) {

    var id: Int = 0

    // load from file
    constructor(path: String) : this(0, 0, ByteArray(0)) {
        val (w, h, p) = loadTexture(path)
        width = w
        height = h
        pixels = p
        id = uploadToGPU()
    }

    // upload pixels to gpu
    private fun uploadToGPU(): Int {
        val textureId = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, textureId)

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)

        glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)

        val buffer = BufferUtils.createByteBuffer(pixels.size)
        buffer.put(pixels).flip()

        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGB,
            width,
            height,
            0,
            GL_RGB,
            GL_UNSIGNED_BYTE,
            buffer
        )

        glBindTexture(GL_TEXTURE_2D, 0)
        return textureId
    }

    // load image
    private fun loadTexture(path: String): Triple<Int, Int, ByteArray> {
        val stream = javaClass.classLoader.getResourceAsStream(path)
            ?: error("texture not found: $path")

        val image = ImageIO.read(stream)
        val width = image.width
        val height = image.height
        val pixels = ByteArray(width * height * 3)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val rgb = image.getRGB(x, y)
                val i = (y * width + x) * 3

                pixels[i]     = ((rgb shr 16) and 0xFF).toByte() // r
                pixels[i + 1] = ((rgb shr 8) and 0xFF).toByte()  // g
                pixels[i + 2] = (rgb and 0xFF).toByte()          // b
            }
        }

        return Triple(width, height, pixels)
    }

    fun dispose() {
        glDeleteTextures(id)
    }

    companion object {
        fun createFromData(width: Int, height: Int, pixels: ByteArray): Texture {
            return Texture(width, height, pixels)
        }
    }
}