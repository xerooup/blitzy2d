package org.xeroup.blitzy.core

import org.xeroup.blitzy.graphics.DrawContext

abstract class Scene {
    open fun create() {}
    open fun update(delta: Float) {}
    open fun render(draw: DrawContext) {}
    open fun dispose() {}
}