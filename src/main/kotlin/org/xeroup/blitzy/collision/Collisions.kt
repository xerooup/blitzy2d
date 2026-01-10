package org.xeroup.blitzy.collision

object Collisions {
    fun rectRect(a: Hitbox, b: Hitbox): Boolean = a.intersects(b)

    fun pointRect(px: Float, py: Float, rect:Hitbox): Boolean = rect.contains(px, py)
}