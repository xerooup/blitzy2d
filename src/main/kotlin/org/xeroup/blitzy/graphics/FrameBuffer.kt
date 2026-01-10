package org.xeroup.blitzy.graphics

// stores rgb pixels (24-bit color)
class FrameBuffer(val width: Int, val height: Int) {
    // byte array: 3 bytes per pixel (r, g, b)
    private val pixels = ByteArray(width * height * 3)

    // fill all pixels with color
    fun clear(color: Color) {
        val r = (color.r * 255).toInt().toByte()
        val g = (color.g * 255).toInt().toByte()
        val b = (color.b * 255).toInt().toByte()

        for (i in pixels.indices step 3) {
            pixels[i] = r
            pixels[i + 1] = g
            pixels[i + 2] = b
        }
    }

    // set one pixel at x,y to color
    fun setPixel(x: Int, y: Int, color: Color) {
        if (x < 0 || x >= width || y < 0 || y >= height) return

        val index = (y * width + x) * 3
        pixels[index] = (color.r * 255).toInt().toByte()
        pixels[index + 1] = (color.g * 255).toInt().toByte()
        pixels[index + 2] = (color.b * 255).toInt().toByte()
    }

    // get pixel array (engine only)
    internal fun getPixels(): ByteArray = pixels
}