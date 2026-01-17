package io.github.xerooup.blitzy.core.camera

import io.github.xerooup.blitzy.entity.Entity

class Camera(
    private val viewportWidth: Int,
    private val viewportHeight: Int
) {

    var x: Float = 0f
        private set
    var y: Float = 0f
        private set

    private var target: Entity? = null

    // settings
    var smoothing: Float = 1f
    var zoom: Float = 1f

    // shake
    private var shakeTime = 0f
    private var shakePower = 0f
    var shakeX = 0f
        private set

    var shakeY = 0f
        private set

    // bind camera to entity
    fun follow(entity: Entity) {
        target = entity
    }

    // unbind camera
    fun unfollow() {
        target = null
    }

    fun shake(power: Float, time: Float) {
        shakePower = power
        shakeTime = time
    }

    // update camera position (center target on screen)
    fun update(delta: Float) {
        val t = target ?: return

        val targetX = t.x - (viewportWidth / 2f) / zoom
        val targetY = t.y - (viewportHeight / 2f) / zoom

        x += (targetX - x) * smoothing
        y += (targetY - y) * smoothing

        // reset shake offset
        shakeX = 0f
        shakeY = 0f

        if (shakeTime > 0f) {
            shakeTime -= delta
            shakeX = (Math.random().toFloat() * 2f - 1f) * shakePower
            shakeY = (Math.random().toFloat() * 2f - 1f) * shakePower
        }
    }
}