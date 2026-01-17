package io.github.xerooup.blitzy.graphics.objects

class Tileset(val texture: Texture, val tileWidth: Int, val tileHeight: Int) {
    val tilesPerRow = texture.width / tileWidth
    val totalRows = texture.height / tileHeight
    val totalTiles = tilesPerRow * totalRows

    // get tile by index (0, 1, 2, ...)
    fun getTile(index: Int): Tile {
        require(index in 0 until totalTiles) { "tile index $index out of range (0..${totalTiles-1})" }
        val col = index % tilesPerRow
        val row = index / tilesPerRow
        return Tile(texture, col * tileWidth, row * tileHeight, tileWidth, tileHeight)
    }

    // get tile by column and row
    fun getTile(col: Int, row: Int): Tile {
        require(col in 0 until tilesPerRow) { "column $col out of range" }
        require(row in 0 until totalRows) { "row $row out of range" }
        return getTile(row * tilesPerRow + col)
    }
}

data class Tile(
    val texture: Texture,
    val srcX: Int,  // x in texture
    val srcY: Int,  // y in texture
    val width: Int,
    val height: Int
)