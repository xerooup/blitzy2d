package org.xeroup.blitzy.entity

import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.collision.Hitbox

abstract class Entity {
    var x: Float = 0f
    var y: Float = 0f
    var width: Float = 32f
    var height: Float = 32f

    open fun create() {}
    open fun update(delta: Float) {}
    open fun render(draw: DrawContext) {}

    fun getBounds(): Hitbox = Hitbox(x, y, width, height)

    fun intersects(other: Entity): Boolean =
        getBounds().intersects(other.getBounds())
}