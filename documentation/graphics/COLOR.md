# Colors
in Blitzy there are ready-made colors and color constructors that use HEX<br><br>
ready-made colors:
```kt 
import org.xeroup.monoenigne.graphics.Color

Color.WHITE
Color.BLACK
Color.RED
Color.GREEN
Color.BLUE
```
hex colors:
```kt 
import org.xeroup.monoenigne.graphics.Color
// format: 0xRRGGBB

Color(0xFFFFFF) // white
Color(0x000000) // black
Color(0xFF0000) // red
Color(0x00FF00) // green
Color(0x0000FF) // blue
// and any other hex colors
```
example in code:
```kt 
// ...code...
override fun render(draw: DrawContext) {
    // draw white circle
    draw.circle(350, 250, 12, Color.WHITE)
    // or:
    draw.circle(350, 250, 12, Color(0xFFFFFF))
}
```