# Map Loader
using the MapLoader class you can load your maps saved as a CSV file<br><br>
let's see how to load maps from csv:
```kt 
import io.github.xerooup.blitzy.core.MapLoader

class MyGame : Game {
    private lateinit var tileset: Tileset
    private lateinit var tilemap: TileMap
    private val mapLoader = MapLoader()

    override fun create() {
        val texture = Texture("PATH/FROM/RESOURCES")
        
        tileset = Tileset(texture, 32, 32)
        
        tilemap = mapLoader.loadMap("level.csv")
        // or create a map using text
        tilemap = mapLoader.loadMapFromText("""
            tile_id, tile_id, tile_id
            tile_id, tile_id, tile_id
            tile_id, tile_id, tile_id
        """.trimIndent()) // .trimIndent() required!
    }
}
```
<br>
level.csv:
<br>

```csv
tile_id, tile_id, tile_id
tile_id, tile_id, tile_id
tile_id, tile_id, tile_id
```
<br>
