# Tilemap
a tilemap is a grid that builds your game world<br><br>
let's see how to manage tiles and create a tilemap:
```kt 
import io.github.xerooup.blitzy.graphics.objects.Tileset

class MyGame : Game {
    private lateinit var tileset: Tileset
    private lateinit var tilemap: TileMap

    override fun create() {
        val texture = Texture("PATH/FROM/RESOURCES")

        tileset = Tileset(texture, TILE_WIDTH, TILE_HEIGHT)
        tilemap = TileMap(MAP_WIDTH, MAP_HEIGHT, TILE_SIZE)

        // useful methods:
        tilemap.setTile(POS1, POS2, TILE_ID)
        tilemap.getTile(POS1, POS2)
        tilemap.clear(TILE_ID)
    }
    override fun render(draw: DrawContext) {
        draw.tilemap(tilemap, tileset, X, Y)
        // or render one tile:
        draw.tile(TILE, X, Y)
    }
}
```