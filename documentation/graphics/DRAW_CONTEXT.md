# Drawing objects (DrawContext)
using DrawContext, we can draw figures or textures on our game screen<br><br>
let's not write the full code, but instead show an example of its usage in the **render** function right away:
```kt 
// all values written in uppercase letters are ordinary values, 
// while those written in regular case are classes or constructors.
// example: Texture -> Texture("PATH")
// or: X -> 12
import org.xeroup.blitzy.graphics.DrawContext

override fun render(draw: DrawContext) {
    // set pixel color
    draw.pixel(X, Y, Color)
    
    // draw rectangle
    // filled
    draw.fillRect(X, Y, WIDTH, HEIGHT, Color)
    // not filled
    draw.rect(X, Y, WIDTH, HEIGHT, Color)
    
    // draw circle
    // filled
    draw.fillCircle(X, Y, radius, Color)
    // not filled
    draw.circle(X, Y, radius, Color)
    
    // draw line
    draw.line(X1, Y1, X2, Y2, WIDTH, Color)
    
    // draw texture
    // with a regular size
    draw.texture(Texture, X, Y)
    // with a custom size
    draw.texture(Texture, X, Y, WIDTH, HEIGHT)
    
    // draw text
    draw.text(Text, Font, X, Y)
}
```
