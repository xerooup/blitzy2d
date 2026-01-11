package org.xeroup.blitzy.graphics.objects

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.BufferUtils
import javax.imageio.ImageIO
import java.nio.ByteBuffer

class Texture(var width: Int, var height: Int, var pixels: ByteArray) {
    var id: Int  // OpenGL texture id

    // load from file
    constructor(path: String) : this(0, 0, ByteArray(0)) {
        val (w, h, p) = loadTexture(path)
        this.width = w
        this.height = h
        this.pixels = p
        this.id = uploadToGPU()
    }

    init {
        id = uploadToGPU()
    }

    // upload pixels to GPU
    private fun uploadToGPU(): Int {
        val textureId = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, textureId)

        // pixel-art settings
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)

        // upload pixel data
        val buffer = BufferUtils.createByteBuffer(pixels.size)
        buffer.put(pixels)
        buffer.flip()

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer)
        glBindTexture(GL_TEXTURE_2D, 0)

        return textureId
    }

    // load image file
    private fun loadTexture(path: String): Triple<Int, Int, ByteArray> {
        val inputStream = javaClass.classLoader.getResourceAsStream(path)
            ?: throw RuntimeException("texture not found: $path")

        val image = ImageIO.read(inputStream)
        val width = image.width
        val height = image.height
        val pixels = ByteArray(width * height * 3)  // RGB

        for (y in 0 until height) {
            for (x in 0 until width) {
                val rgb = image.getRGB(x, y)
                val a = (rgb shr 24) and 0xFF  // alpha
                val index = (y * width + x) * 3

                // transparent pixels become black
                if (a < 200) {
                    pixels[index] = 0
                    pixels[index + 1] = 0
                    pixels[index + 2] = 0
                } else {
                    // extract RGB
                    pixels[index] = ((rgb shr 16) and 0xFF).toByte()
                    pixels[index + 1] = ((rgb shr 8) and 0xFF).toByte()
                    pixels[index + 2] = (rgb and 0xFF).toByte()
                }
            }
        }

        return Triple(width, height, pixels)
    }

    // free GPU memory
    fun dispose() {
        glDeleteTextures(id)
    }

    companion object {
        // create from raw data
        fun createFromData(width: Int, height: Int, pixels: ByteArray): Texture {
            return Texture(width, height, pixels)
        }
    }
}