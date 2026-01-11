# Tilemap
a tilemap is a grid that builds your game world<br><br>
let's see how to manage tiles and create a tilemap:
```kt 
import org.xeroup.blitzy.graphics.objects.Tileset

class MyGame : Game {
    private lateinit var tileset: Tileset
    private lateinit var tilemap: TileMap
    
    override fun create() {
        // load tileset texture
        val texture = Texture("PATH/FROM/RESOURCES")

        // create tileset
        tileset = Tileset(texture, 32, 32) // each tile will be 32x32
        
        tilemap = TileMap(5, 5, 32) // tilemap size of 5x5 tiles
        
        // useful methods:
        tilemap.setTile(POS1, POS2, TILE_ID) // TILE_ID - the tile number in the loaded tileset
        tilemap.getTile(POS1, POS2) // tile ID by position
        tilemap.clear(TILE_ID) // fill the entire tilemap with one tile
    }
    override fun render(draw: DrawContext) {
        draw.tilemap(tilemap, tileset, X, Y) // render tilemap
        // or render one tile:
        draw.tile(TILE, X, Y, TINT) // get tile from tileset.getTile()
    }
}
```