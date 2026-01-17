package io.github.xerooup.blitzy.graphics.objects

class TileMap(val width: Int, val height: Int, val tileSize: Int = 32) {
    private val tiles = IntArray(width * height) { 0 }

    // set tile at position
    fun setTile(x: Int, y: Int, tileId: Int) {
        if (x in 0 until width && y in 0 until height) {
            tiles[y * width + x] = tileId
        }
    }

    // get tile id at position
    fun getTile(x: Int, y: Int): Int {
        if (x !in 0 until width || y !in 0 until height) return -1
        return tiles[y * width + x]
    }

    // fill all tiles with value
    fun clear(tileId: Int = 0) {
        tiles.fill(tileId)
    }
}