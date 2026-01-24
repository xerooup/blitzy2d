# DrawContext
using DrawContext, we can draw figures or textures on our game screen<br><br>
let's not write the full code, but instead show an example of its usage in the **render** function right away:
```kt 
import io.github.xerooup.blitzy.graphics.DrawContext

override fun render(draw: DrawContext) {
    draw.pixel(X, Y, Color)
    
    draw.fillRect(X, Y, WIDTH, HEIGHT, Color)
    draw.rect(X, Y, WIDTH, HEIGHT, Color)
    
    draw.fillCircle(X, Y, radius, Color)
    draw.circle(X, Y, radius, Color)
    
    draw.line(X1, Y1, X2, Y2, WIDTH, Color)
    
    draw.texture(Texture, X, Y)
    draw.texture(Texture, X, Y, WIDTH, HEIGHT)
    
    draw.text(Text, Font, X, Y)
}
```
