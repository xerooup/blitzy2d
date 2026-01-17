package io.github.xerooup.blitzy.graphics

class Color(val r: Float, val g: Float, val b: Float) {
    constructor(hex: Int) : this(
        ((hex shr 16) and 0xFF) / 255f,
        ((hex shr 8) and 0xFF) / 255f,
        (hex and 0xFF) / 255f
    )

    constructor(r: Int, g: Int, b: Int) : this(r / 255f, g / 255f, b / 255f)

    companion object {
        // basics colors
        val WHITE = Color(0xFFFFFF)
        val BLACK = Color(0x000000)
        val RED = Color(0xFF0000)
        val GREEN = Color(0x00FF00)
        val BLUE = Color(0x0000FF)
    }

    internal fun toRgb888(): Int {
        return ((r * 255).toInt() shl 16) or
                ((g * 255).toInt() shl 8) or
                ((b * 255).toInt())
    }
}