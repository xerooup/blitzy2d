package org.xeroup.blitzy.collision

import java.lang.StrictMath.abs

data class Hitbox(var x: Float, var y: Float, var width: Float, var height: Float) {
    fun intersects(other: Hitbox): Boolean {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y
    }

    fun contains(px: Float, py: Float): Boolean {
        return px >= x && px < x + width &&
                py >= y && py < y + height
    }

    fun resolveCollision(other: Hitbox): Vector2 {
        val dx = (x + width/2) - (other.x + other.width/2)
        val dy = (y + height/2) - (other.y + other.height/2)

        val overlapX = width/2 + other.width/2 - abs(dx)
        val overlapY = height/2 + other.height/2 - abs(dy)

        return if (overlapX < overlapY) {
            Vector2(if (dx > 0) overlapX else -overlapX, 0f)
        } else {
            Vector2(0f, if (dy > 0) overlapY else -overlapY)
        }
    }

    fun left(): Float = x
    fun right(): Float = x + width
    fun top(): Float = y
    fun bottom(): Float = y + height

    fun centerX(): Float = x + width / 2
    fun centerY(): Float = y + height / 2
}

data class Vector2(var x: Float, var y: Float)