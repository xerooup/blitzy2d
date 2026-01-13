package org.xeroup.blitzy.core.camera

import org.xeroup.blitzy.entity.Entity

class Camera(
    private val viewportWidth: Int,
    private val viewportHeight: Int
) {

    var x: Float = 0f
        private set
    var y: Float = 0f
        private set

    private var target: Entity? = null

    var smoothing: Float = 1f
    var zoom: Float = 1f

    // bind camera to entity
    fun follow(entity: Entity) {
        target = entity
    }

    // unbind camera
    fun unfollow() {
        target = null
    }

    // update camera position (center target on screen)
    fun update() {
        val t = target ?: return

        val targetX = t.x - (viewportWidth / 2f) / zoom
        val targetY = t.y - (viewportHeight / 2f) / zoom

        x += (targetX - x) * smoothing
        y += (targetY - y) * smoothing
    }
}