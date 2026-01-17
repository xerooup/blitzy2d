package io.github.xerooup.blitzy.entity

import io.github.xerooup.blitzy.graphics.DrawContext
import io.github.xerooup.blitzy.collision.Hitbox

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

    fun collidesAny(entities: List<Entity>): Entity? {
        val entityBounds = getBounds()
        for (entity in entities) {
            if (entity !== this && entityBounds.intersects(entity.getBounds())) {
                return entity
            }
        }
        return null
    }

    fun collidesAny(hitboxes: List<Hitbox>): Hitbox? {
        val entityBounds = getBounds()
        for (hitbox in hitboxes) {
            if (entityBounds.intersects(hitbox)) {
                return hitbox
            }
        }
        return null
    }
}