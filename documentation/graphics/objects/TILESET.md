# Tileset
with tilesets we can slice our textures into tiles<br><br>
let's create a tileset:
```kt 
import io.github.xerooup.blitzy.graphics.objects.Tileset

class MyGame : Game {
    private lateinit var tileset: Tileset
    private lateinit var texture: Texture
    
    override fun create() {
        texture = Texture("PATH/FROM/RESOURCES")
        tileset = Tileset(texture, 32, 32) // each tile will be 32x32
    }
}
```
<br>
we can also get tiles:
<br>

```kt 
tileset.getTile(COLUMN, ROW)
tileset.getTile(INDEX)
```