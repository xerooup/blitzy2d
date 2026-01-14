package org.xeroup.blitzy.core

import org.xeroup.blitzy.graphics.objects.TileMap

class MapLoader {
    // load from CSV file in resources
    fun loadMap(csvPath: String): TileMap {
        val inputStream = javaClass.classLoader.getResourceAsStream(csvPath)
            ?: throw RuntimeException("map file not found: $csvPath")

        val content = inputStream.bufferedReader().use { it.readText() }
        return parse(content)
    }

    // load from text
    fun loadMapFromText(csv: String): TileMap = parse(csv)

    // parse CSV
    private fun parse(csvContent: String): TileMap {
        val rows = csvContent.trim().lines()
        val height = rows.size
        if (height == 0) throw IllegalArgumentException("empty csv")

        val firstRow = rows[0].split(',')
        val width = firstRow.size

        val tilemap = TileMap(width, height)

        for (y in rows.indices) {
            val cells = rows[y].split(',')
            if (cells.size != width) {
                throw IllegalArgumentException("row $y has ${cells.size} cells, expected $width")
            }

            for (x in cells.indices) {
                val cell = cells[x].trim()
                val tileId = cell.toIntOrNull() ?: -1
                if (tileId >= 0) {
                    tilemap.setTile(x, y, tileId)
                }
            }
        }

        return tilemap
    }
}