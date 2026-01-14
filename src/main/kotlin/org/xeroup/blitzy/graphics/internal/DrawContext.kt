package org.xeroup.blitzy.graphics.internal

import org.xeroup.blitzy.core.camera.Camera
import org.xeroup.blitzy.graphics.Color
import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.graphics.FrameBuffer
import org.xeroup.blitzy.graphics.objects.Texture
import org.xeroup.blitzy.graphics.objects.Tile
import org.xeroup.blitzy.graphics.objects.TileMap
import org.xeroup.blitzy.graphics.objects.Tileset

class DrawContextImpl(private val buffer: FrameBuffer) : DrawContext {
    private var camera: Camera? = null

    // camera
    override fun camera(camera: Camera) {
        this.camera = camera
    }

    override fun resetCamera() {
        this.camera = null
    }

    // world -> screen
    private fun applyCamera(x: Float, y: Float): Pair<Int, Int> {
        val cam = camera ?: return x.toInt() to y.toInt()

        val sx = ((x - cam.x + cam.shakeX) * cam.zoom).toInt()
        val sy = ((y - cam.y + cam.shakeY) * cam.zoom).toInt()

        return sx to sy
    }

    // basics figures
    override fun pixel(x: Int, y: Int, color: Color) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        buffer.setPixel(sx, sy, color)
    }

    override fun fillRect(x: Int, y: Int, width: Int, height: Int, color: Color) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f
        val w = (width * zoom).toInt()
        val h = (height * zoom).toInt()

        for (dy in 0 until h) {
            for (dx in 0 until w) {
                buffer.setPixel(sx + dx, sy + dy, color)
            }
        }
    }

    override fun rect(x: Int, y: Int, width: Int, height: Int, color: Color) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f
        val w = (width * zoom).toInt()
        val h = (height * zoom).toInt()

        for (dx in 0 until w) {
            buffer.setPixel(sx + dx, sy, color)
            buffer.setPixel(sx + dx, sy + height - 1, color)
        }
        for (dy in 0 until h) {
            buffer.setPixel(sx, sy + dy, color)
            buffer.setPixel(sx + width - 1, sy + dy, color)
        }
    }

    override fun circle(x: Int, y: Int, radius: Int, color: Color) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f
        var cx = (radius * zoom).toInt()
        var cy = 0
        var err = 0

        while (cx >= cy) {
            buffer.setPixel(sx + cx, sy + cy, color)
            buffer.setPixel(sx + cy, sy + cx, color)
            buffer.setPixel(sx - cy, sy + cx, color)
            buffer.setPixel(sx - cx, sy + cy, color)
            buffer.setPixel(sx - cx, sy - cy, color)
            buffer.setPixel(sx - cy, sy - cx, color)
            buffer.setPixel(sx + cy, sy - cx, color)
            buffer.setPixel(sx + cx, sy - cy, color)

            cy++
            err += 1 + 2 * cy
            if (2 * (err - cx) + 1 > 0) {
                cx--
                err += 1 - 2 * cx
            }
        }
    }

    override fun fillCircle(x: Int, y: Int, radius: Int, color: Color) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f
        val r = (radius * zoom).toInt()
        val r2 = r * r

        for (dy in -r..r) {
            for (dx in -r..r) {
                if (dx * dx + dy * dy <= r2) {
                    buffer.setPixel(sx + dx, sy + dy, color)
                }
            }
        }
    }

    override fun line(x1: Int, y1: Int, x2: Int, y2: Int, width: Int, color: Color) {
        val dx = kotlin.math.abs(x2 - x1)
        val dy = kotlin.math.abs(y2 - y1)

        val sx = if (x1 < x2) 1 else -1
        val sy = if (y1 < y2) 1 else -1

        var err = dx - dy
        var cx = x1
        var cy = y1

        val half = width / 2

        while (true) {
            // thickness
            for (ox in -half..half) {
                for (oy in -half..half) {
                    pixel(cx + ox, cy + oy, color)
                }
            }

            if (cx == x2 && cy == y2) break

            val e2 = 2 * err
            if (e2 > -dy) {
                err -= dy
                cx += sx
            }
            if (e2 < dx) {
                err += dx
                cy += sy
            }
        }
    }

    override fun texture(texture: Texture, x: Int, y: Int, tint: Color) {
        texture(texture, x, y, texture.width, texture.height, tint)
    }

    override fun texture(
        texture: Texture,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        tint: Color
    ) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f
        val w = (width * zoom).toInt()
        val h = (height * zoom).toInt()

        val scaleX = texture.width.toFloat() / w
        val scaleY = texture.height.toFloat() / h

        for (dy in 0 until h) {
            for (dx in 0 until w) {
                val srcX = (dx * scaleX).toInt()
                val srcY = (dy * scaleY).toInt()
                if (srcX !in 0 until texture.width || srcY !in 0 until texture.height) continue

                val idx = (srcY * texture.width + srcX) * 3
                val r = texture.pixels[idx].toInt() and 0xFF
                val g = texture.pixels[idx + 1].toInt() and 0xFF
                val b = texture.pixels[idx + 2].toInt() and 0xFF
                if (r == 0 && g == 0 && b == 0) continue

                buffer.setPixel(
                    sx + dx,
                    sy + dy,
                    Color((r / 255f) * tint.r, (g / 255f) * tint.g, (b / 255f) * tint.b)
                )
            }
        }
    }

    override fun tile(tile: Tile, x: Int, y: Int, tint: Color) {
        tile(tile, x, y, tile.width, tile.height, tint)
    }

    override fun tile(tile: Tile, x: Int, y: Int, width: Int, height: Int, tint: Color) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f
        val w = (width * zoom).toInt()
        val h = (height * zoom).toInt()

        val scaleX = tile.width.toFloat() / w
        val scaleY = tile.height.toFloat() / h

        for (dy in 0 until h) {
            for (dx in 0 until w) {
                val srcX = tile.srcX + (dx * scaleX).toInt()
                val srcY = tile.srcY + (dy * scaleY).toInt()

                if (srcX < tile.srcX || srcX >= tile.srcX + tile.width ||
                    srcY < tile.srcY || srcY >= tile.srcY + tile.height
                ) continue

                val srcIndex = (srcY * tile.texture.width + srcX) * 3

                val r = tile.texture.pixels[srcIndex].toInt() and 0xFF
                val g = tile.texture.pixels[srcIndex + 1].toInt() and 0xFF
                val b = tile.texture.pixels[srcIndex + 2].toInt() and 0xFF

                if (r == 0 && g == 0 && b == 0) continue

                buffer.setPixel(
                    sx + dx,
                    sy + dy,
                    Color((r / 255f) * tint.r, (g / 255f) * tint.g, (b / 255f) * tint.b)
                )
            }
        }
    }

    override fun tilemap(tilemap: TileMap, tileset: Tileset, x: Int, y: Int) {
        for (ty in 0 until tilemap.height) {
            for (tx in 0 until tilemap.width) {
                val tileId = tilemap.getTile(tx, ty)
                if (tileId >= 0) {
                    val tile = tileset.getTile(tileId)
                    // draw tilemap
                    tile(tile, x + tx * tilemap.tileSize, y + ty * tilemap.tileSize)
                }
            }
        }
    }
}