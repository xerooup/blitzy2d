package io.github.xerooup.blitzy.graphics.internal

import io.github.xerooup.blitzy.core.camera.Camera
import io.github.xerooup.blitzy.graphics.Color
import io.github.xerooup.blitzy.graphics.DrawContext
import io.github.xerooup.blitzy.graphics.FrameBuffer
import io.github.xerooup.blitzy.graphics.objects.Texture
import io.github.xerooup.blitzy.graphics.objects.Tile
import io.github.xerooup.blitzy.graphics.objects.TileMap
import io.github.xerooup.blitzy.graphics.objects.Tileset
import kotlin.math.abs

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
        val dx = abs(x2 - x1)
        val dy = abs(y2 - y1)

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

    override fun texture(texture: Texture, x: Int, y: Int) {
        texture(texture, x, y, texture.width, texture.height)
    }

    override fun texture(
        texture: Texture,
        x: Int,
        y: Int,
        width: Int,
        height: Int
    ) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f

        val w = (width * zoom).toInt()
        val h = (height * zoom).toInt()

        val scaleX = texture.width.toFloat() / w
        val scaleY = texture.height.toFloat() / h

        for (dy in 0 until h) {
            val srcY = (dy * scaleY).toInt().coerceIn(0, texture.height - 1)

            for (dx in 0 until w) {
                val srcX = (dx * scaleX).toInt().coerceIn(0, texture.width - 1)

                val idx = (srcY * texture.width + srcX) * 3
                val r = texture.pixels[idx].toInt() and 0xFF
                val g = texture.pixels[idx + 1].toInt() and 0xFF
                val b = texture.pixels[idx + 2].toInt() and 0xFF

                buffer.setPixel(
                    sx + dx,
                    sy + dy,
                    Color(r / 255f, g / 255f, b / 255f)
                )
            }
        }
    }

    override fun tile(tile: Tile, x: Int, y: Int) {
        tile(tile, x, y, tile.width, tile.height)
    }

    override fun tile(tile: Tile, x: Int, y: Int, width: Int, height: Int) {
        val (sx, sy) = applyCamera(x.toFloat(), y.toFloat())
        val zoom = camera?.zoom ?: 1f
        val w = (width * zoom).toInt()
        val h = (height * zoom).toInt()

        val scaleX = tile.width.toFloat() / w
        val scaleY = tile.height.toFloat() / h

        for (dy in 0 until h) {
            val srcY = tile.srcY + (dy * scaleY).toInt().coerceIn(tile.srcY, tile.srcY + tile.height - 1)

            for (dx in 0 until w) {
                val srcX = tile.srcX + (dx * scaleX).toInt().coerceIn(tile.srcX, tile.srcX + tile.width - 1)

                val srcIndex = (srcY * tile.texture.width + srcX) * 3

                val r = tile.texture.pixels[srcIndex].toInt() and 0xFF
                val g = tile.texture.pixels[srcIndex + 1].toInt() and 0xFF
                val b = tile.texture.pixels[srcIndex + 2].toInt() and 0xFF

                if (r == 0 && g == 0 && b == 0) continue

                buffer.setPixel(
                    sx + dx,
                    sy + dy,
                    Color(r / 255f, g / 255f, b / 255f)
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
                    tile(tile, x + tx * tilemap.tileSize, y + ty * tilemap.tileSize)
                }
            }
        }
    }
}