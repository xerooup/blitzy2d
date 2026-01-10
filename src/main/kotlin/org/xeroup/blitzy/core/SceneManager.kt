package org.xeroup.blitzy.core

import org.xeroup.blitzy.graphics.DrawContext

class SceneManager {
    private val scenes = mutableMapOf<String, Scene>()
    private var currentScene: Scene? = null

    fun addScene(name: String, scene: Scene) {
        scenes[name] = scene
    }

    fun setScene(name: String) {
        currentScene?.dispose()
        currentScene = scenes[name] ?: throw RuntimeException("scene not found: $name")
        currentScene?.create()
    }

    fun update(delta: Float) {
        currentScene?.update(delta)
    }

    fun render(draw: DrawContext) {
        currentScene?.render(draw)
    }

    fun dispose() {
        currentScene?.dispose()
        scenes.values.forEach { it.dispose() }
    }
}