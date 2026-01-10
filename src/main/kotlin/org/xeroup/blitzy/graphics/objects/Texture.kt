package org.xeroup.blitzy.graphics.objects

import javax.imageio.ImageIO
import org.xeroup.blitzy.graphics.Color
import java.awt.image.BufferedImage

class Texture private constructor(
    var width: Int,
    var height: Int,
    var pixels: ByteArray
) {
    constructor(path: String) : this(0, 0, ByteArray(0)) {
        val (w, h, p) = loadTexture(path)
        this.width = w
        this.height = h
        this.pixels = p
    }

    companion object {
        fun createFromData(width: Int, height: Int, pixels: ByteArray): Texture {
            return Texture(width, height, pixels)
        }

        // create monochrome texture
        fun createMonochrome(width: Int, height: Int, data: ByteArray): Texture {
            val rgbPixels = ByteArray(width * height * 3)
            for (i in data.indices) {
                val value = if (data[i].toInt() == 1) 255 else 0
                rgbPixels[i * 3] = value.toByte()
                rgbPixels[i * 3 + 1] = value.toByte()
                rgbPixels[i * 3 + 2] = value.toByte()
            }
            return Texture(width, height, rgbPixels)
        }
    }

    private fun loadTexture(path: String): Triple<Int, Int, ByteArray> {
        val inputStream = javaClass.classLoader.getResourceAsStream(path)
            ?: throw RuntimeException("texture not found: $path")

        val image = ImageIO.read(inputStream)
        val width = image.width
        val height = image.height
        val pixels = ByteArray(width * height * 3)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val rgb = image.getRGB(x, y)
                val a = (rgb shr 24) and 0xFF

                val index = (y * width + x) * 3
                if (a < 200) {
                    // transparent
                    pixels[index] = 0
                    pixels[index + 1] = 0
                    pixels[index + 2] = 0
                } else {
                    // opaque
                    pixels[index] = ((rgb shr 16) and 0xFF).toByte()
                    pixels[index + 1] = ((rgb shr 8) and 0xFF).toByte()
                    pixels[index + 2] = (rgb and 0xFF).toByte()
                }
            }
        }

        return Triple(width, height, pixels)
    }
}