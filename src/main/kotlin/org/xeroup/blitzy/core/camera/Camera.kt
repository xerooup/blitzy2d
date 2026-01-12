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
        x = t.x - viewportWidth / 2f
        y = t.y - viewportHeight / 2f
    }
}