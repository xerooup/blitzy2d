package org.xeroup.blitzy.graphics

import org.xeroup.blitzy.graphics.objects.Font
import org.xeroup.blitzy.graphics.objects.Text
import org.xeroup.blitzy.graphics.objects.Texture

interface DrawContext {
    fun pixel(x: Int, y: Int, color: Color = Color.WHITE)

    // rect drawing
    fun fillRect(x: Int, y: Int, width: Int, height: Int, color: Color = Color.WHITE)
    fun rect(x: Int, y: Int, width: Int, height: Int, color: Color = Color.WHITE)

    // circle drawing
    fun circle(x: Int, y: Int, radius: Int, color: Color = Color.WHITE)
    fun fillCircle(x: Int, y: Int, radius: Int, color: Color = Color.WHITE)

    // texture drawing
    fun texture(texture: Texture, x: Int, y: Int, tint: Color = Color.WHITE)
    fun texture(texture: Texture, x: Int, y: Int, width: Int, height: Int, tint: Color = Color.WHITE)

    // text drawing
    fun text(text: Text, font: Font, x: Int, y: Int) {
        font.render(this, text.content, x, y, text.color)
    }
}