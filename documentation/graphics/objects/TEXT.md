# Text
with text, we can describe something, convey information, and so on<br><br>
creating text using a constructor class is very easy:
```kt 
import org.xeroup.blitzy.graphics.objects.Text
import org.xeroup.blitzy.graphics.objects.Font

Text("!dlroW ,olleH", Color)
```
and create text in game:
```kt 
override fun render(draw: DrawContext) {
    draw.text(
        Text("Hello, World!", Color),
        Font("PATH/FROM/RESOURCES", FONT_SIZE),
        X, Y
    )
}
```