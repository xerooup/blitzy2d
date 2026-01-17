package io.github.xerooup.blitzy.core

import io.github.xerooup.blitzy.core.internal.Engine
import io.github.xerooup.blitzy.graphics.Color
import io.github.xerooup.blitzy.graphics.DrawContext

data class Settings(
    var width: Int = 800,
    var height: Int = 600,
    var title: String = "blitzy game",
    var decorated: Boolean = true,
    var targetFPS: Int = 60,
    var background: Color = Color.WHITE,
    var iconPath: String? = null,
    var windowX: Int? = null,
    var windowY: Int? = null
)

abstract class Game {
    abstract fun settings(settings: Settings)
    open fun create() {}
    open fun update(delta: Float) {}
    open fun render(draw: DrawContext) {}

    companion object {
        fun run(game: Game) {
            val settings = Settings()
            game.settings(settings)
            Engine(settings, game).run()
        }
        fun stop() {
            Engine.forceStop()
        }
        var fps: Int = 0
            internal set

        var uptime: Float = 0f
            internal set
    }
}