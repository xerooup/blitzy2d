# Textures
with textures, we can transfer our images directly into the game.<br><br>
let's not write the full code, but instead check an examples:
1. create late init texture variable
```kt 
import org.xeroup.blitzy.graphics.objects.Texture

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
    // if the texture is not saved in a variable
    draw.texture(Texture("PATH/FROM/RESOURCES"), X, Y)
    
    // or:
    
    draw.texture(spriteTexture, X, Y, WIDTH, HEIGHT)
    // if the texture is not saved in a variable
    draw.texture(Texture("PATH/FROM/RESOURCES"), X, Y, WIDTH, HEIGHT)

    // we can also set the tint of the texture:
    
    draw.texture(spriteTexture, X, Y, WIDTH, HEIGHT, Color)
    // if the texture is not saved in a variable
    draw.texture(Texture("PATH/FROM/RESOURCES"), X, Y, WIDTH, HEIGHT, Color)
    
    // recommendation: save textures in variables
}
```