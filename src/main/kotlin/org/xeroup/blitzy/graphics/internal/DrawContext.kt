package org.xeroup.blitzy.graphics.internal

import org.xeroup.blitzy.graphics.Color
import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.graphics.FrameBuffer
import org.xeroup.blitzy.graphics.objects.Texture

class DrawContextImpl(private val buffer: FrameBuffer) : DrawContext {
    override fun pixel(x: Int, y: Int, color: Color) {
        buffer.setPixel(x, y, color)
    }

    override fun fillRect(x: Int, y: Int, width: Int, height: Int, color: Color) {
        for (dy in 1 until height - 1) {
            for (dx in 1 until width - 1) {
                buffer.setPixel(x + dx, y + dy, color)
            }
        }
    }

    override fun rect(x: Int, y: Int, width: Int, height: Int, color: Color) {
        // top horizontal line
        for (dx in 0 until width) {
            buffer.setPixel(x + dx, y, color)
        }
        // bottom horizontal line
        for (dx in 0 until width) {
            buffer.setPixel(x + dx, y + height - 1, color)
        }
        // left vertical line
        for (dy in 0 until height) {
            buffer.setPixel(x, y + dy, color)
        }
        // right vertical line
        for (dy in 0 until height) {
            buffer.setPixel(x + width - 1, y + dy, color)
        }
    }

    override fun circle(x: Int, y: Int, radius: Int, color: Color) {
        var cx = radius
        var cy = 0
        var err = 0

        while (cx >= cy) {
            buffer.setPixel(x + cx, y + cy, color)
            buffer.setPixel(x + cy, y + cx, color)
            buffer.setPixel(x - cy, y + cx, color)
            buffer.setPixel(x - cx, y + cy, color)
            buffer.setPixel(x - cx, y - cy, color)
            buffer.setPixel(x - cy, y - cx, color)
            buffer.setPixel(x + cy, y - cx, color)
            buffer.setPixel(x + cx, y - cy, color)

            cy++
            err += 1 + 2*cy
            if (2*(err - cx) + 1 > 0) {
                cx--
                err += 1 - 2*cx
            }
        }
    }

    override fun fillCircle(x: Int, y: Int, radius: Int, color: Color) {
        val fillRadius = radius - 1
        if (fillRadius <= 0) return

        val r2 = fillRadius * fillRadius
        for (dy in -fillRadius..fillRadius) {
            for (dx in -fillRadius..fillRadius) {
                if (dx * dx + dy * dy < r2) {
                    buffer.setPixel(x + dx, y + dy, color)
                }
            }
        }
    }

    override fun texture(texture: Texture, x: Int, y: Int, tint: Color) {
        texture(texture, x, y, texture.width, texture.height, tint)
    }

    override fun texture(texture: Texture, x: Int, y: Int, width: Int, height: Int, tint: Color) {
        val scaleX = texture.width.toFloat() / width.toFloat()
        val scaleY = texture.height.toFloat() / height.toFloat()

        for (dy in 0 until height) {
            for (dx in 0 until width) {
                val srcX = (dx * scaleX).toInt()
                val srcY = (dy * scaleY).toInt()

                if (srcX < 0 || srcX >= texture.width || srcY < 0 || srcY >= texture.height) continue

                val srcIndex = (srcY * texture.width + srcX) * 3

                val r = texture.pixels[srcIndex].toInt() and 0xFF
                val g = texture.pixels[srcIndex + 1].toInt() and 0xFF
                val b = texture.pixels[srcIndex + 2].toInt() and 0xFF

                if (r == 0 && g == 0 && b == 0) continue

                // tint
                val textureR = r / 255f
                val textureG = g / 255f
                val textureB = b / 255f

                val finalR = textureR * tint.r
                val finalG = textureG * tint.g
                val finalB = textureB * tint.b

                buffer.setPixel(x + dx, y + dy, Color(finalR, finalG, finalB))
            }
        }
    }
}