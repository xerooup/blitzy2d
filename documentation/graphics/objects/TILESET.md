# Tileset
with tilesets we can slice our textures into tiles<br><br>
let's create a tileset:
```kt 
import org.xeroup.blitzy.graphics.objects.Tileset

class MyGame : Game {
    private lateinit var tileset: Tileset
    
    override fun create() {
        // load tileset texture
        val texture = Texture("PATH/FROM/RESOURCES")

        // create tileset
        tileset = Tileset(texture, 32, 32) // each tile will be 32x32
    }
}
```
<br>
we can also get tiles:
<br>

```kt 
tileset.getTile(COLUMN, ROW) // get tile by column and row
tileset.getTile(INDEX) // get tile by index
```