package io.github.xerooup.blitzy.graphics.objects

import io.github.xerooup.blitzy.graphics.Color
import io.github.xerooup.blitzy.graphics.DrawContext
import java.awt.Font as JavaFont
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import kotlin.text.iterator

class Font private constructor(
    private val texture: Texture,
    val charWidth: Int,
    val charHeight: Int
) {
    constructor(path: String, size: Int) : this(
        generateTextureFromTtf(path, size),
        size,
        size
    )

    private val charsPerRow = texture.width / charWidth

    fun render(draw: DrawContext, text: String, x: Int, y: Int, color: Color = Color.WHITE) {
        var offsetX = 0
        for (ch in text) {
            renderChar(draw, ch, x + offsetX, y, color)
            offsetX += charWidth
        }
    }

    private fun renderChar(draw: DrawContext, ch: Char, x: Int, y: Int, color: Color) {
        val index = ch.code
        if (index < 32 || index > 126) return

        val charIndex = index - 32
        val srcX = (charIndex % charsPerRow) * charWidth
        val srcY = (charIndex / charsPerRow) * charHeight

        for (dy in 0 until charHeight) {
            for (dx in 0 until charWidth) {
                val pixelIndex = ((srcY + dy) * texture.width + (srcX + dx)) * 3
                // check if pixel is not black
                val r = texture.pixels[pixelIndex].toInt() and 0xFF
                val g = texture.pixels[pixelIndex + 1].toInt() and 0xFF
                val b = texture.pixels[pixelIndex + 2].toInt() and 0xFF

                if (r > 0 || g > 0 || b > 0) {
                    draw.pixel(x + dx, y + dy, color)
                }
            }
        }
    }

    companion object {
        private fun generateTextureFromTtf(path: String, size: Int): Texture {
            val inputStream = Font::class.java.classLoader.getResourceAsStream(path)
                ?: throw RuntimeException("font not found: $path")

            val bytes = inputStream.readAllBytes()
            return createTextureFromTtfData(bytes, size)
        }

        private fun createTextureFromTtfData(fontData: ByteArray, size: Int): Texture {
            val byteStream = ByteArrayInputStream(fontData)
            val awtFont = JavaFont.createFont(JavaFont.TRUETYPE_FONT, byteStream)
            val derivedFont = awtFont.deriveFont(size.toFloat())

            val charsPerRow = 16
            val totalChars = 126 - 32 + 1
            val rows = (totalChars + charsPerRow - 1) / charsPerRow

            val charWidth = size
            val charHeight = size
            val imageWidth = charsPerRow * charWidth
            val imageHeight = rows * charHeight

            val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
            val g = image.createGraphics() as Graphics2D
            g.font = derivedFont
            g.color = java.awt.Color.WHITE

            val metrics = g.fontMetrics
            val ascent = metrics.ascent

            for (i in 32..126) {
                val ch = i.toChar()
                val col = (i - 32) % charsPerRow
                val row = (i - 32) / charsPerRow

                g.drawString(ch.toString(),
                    col * charWidth,
                    row * charHeight + ascent)
            }

            g.dispose()

            // convert to rgb texture
            val pixels = ByteArray(imageWidth * imageHeight * 3)
            for (y in 0 until imageHeight) {
                for (x in 0 until imageWidth) {
                    val argb = image.getRGB(x, y)
                    val alpha = (argb shr 24) and 0xFF
                    val index = (y * imageWidth + x) * 3

                    if (alpha > 128) {
                        // white text
                        pixels[index] = 255.toByte()
                        pixels[index + 1] = 255.toByte()
                        pixels[index + 2] = 255.toByte()
                    } else {
                        // transparent
                        pixels[index] = 0
                        pixels[index + 1] = 0
                        pixels[index + 2] = 0
                    }
                }
            }

            return Texture.createFromData(imageWidth, imageHeight, pixels)
        }
    }
}