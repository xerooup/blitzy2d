# Textures
with textures, we can transfer our images directly into the game.<br><br>
let's not write the full code, but instead check an examples:
1. create late init texture variable
```kt 
import io.github.xerooup.blitzy.graphics.objects.Texture

class YourGameClass : Game() {
    private lateinit var spriteTexture: Texture
    // ...code...
}
```
2. render texture
```kt
override fun create() {
    spriteTexture = Texture("PATH/FROM/RESOURCES")
}

override fun render(draw: DrawContext) {
    draw.texture(spriteTexture, X, Y)
    
    // or:
    draw.texture(spriteTexture, X, Y, WIDTH, HEIGHT)

    // we can also set the tint of the texture:
    draw.texture(spriteTexture, X, Y, WIDTH, HEIGHT, Color)
}
```