# Map Loader
using the MapLoader class you can load your maps saved as a CSV file<br><br>
let's see how to load maps from csv:
```kt 
import org.xeroup.blitzy.core.MapLoader

class MyGame : Game {
    private lateinit var tileset: Tileset
    private lateinit var tilemap: TileMap
    private val mapLoader = MapLoader()

    override fun create() {
        // load tileset texture
        val texture = Texture("PATH/FROM/RESOURCES")

        // create tileset
        tileset = Tileset(texture, 32, 32)
        
        // create a map using csv
        // tilemap = mapLoader.loadMap("PATH/FROM/RESOURCES")
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
