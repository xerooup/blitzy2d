package org.xeroup.blitzy.graphics

import org.xeroup.blitzy.core.camera.Camera
import org.xeroup.blitzy.graphics.objects.Font
import org.xeroup.blitzy.graphics.objects.Text
import org.xeroup.blitzy.graphics.objects.Texture
import org.xeroup.blitzy.graphics.objects.Tile
import org.xeroup.blitzy.graphics.objects.TileMap
import org.xeroup.blitzy.graphics.objects.Tileset

interface DrawContext {
    fun pixel(x: Int, y: Int, color: Color = Color.WHITE)

    // rect drawing
    fun fillRect(x: Int, y: Int, width: Int, height: Int, color: Color = Color.WHITE)
    fun rect(x: Int, y: Int, width: Int, height: Int, color: Color = Color.WHITE)

    // circle drawing
    fun circle(x: Int, y: Int, radius: Int, color: Color = Color.WHITE)
    fun fillCircle(x: Int, y: Int, radius: Int, color: Color = Color.WHITE)

    // line drawing
    fun line(x1: Int, y1: Int, x2: Int, y2: Int, width: Int, color: Color)

    // texture drawing
    fun texture(texture: Texture, x: Int, y: Int, tint: Color = Color.WHITE)
    fun texture(texture: Texture, x: Int, y: Int, width: Int, height: Int, tint: Color = Color.WHITE)

    // tile drawing
    fun tilemap(tilemap: TileMap, tileset: Tileset, x: Int, y: Int)
    fun tile(tile: Tile, x: Int, y: Int, tint: Color = Color.WHITE)
    fun tile(tile: Tile, x: Int, y: Int, width: Int, height: Int, tint: Color = Color.WHITE)

    // camera RENDERING
    fun camera(camera: Camera)
    fun resetCamera()

    // text drawing
    fun text(text: Text, font: Font, x: Int, y: Int) {
        font.render(this, text.content, x, y, text.color)
    }
}