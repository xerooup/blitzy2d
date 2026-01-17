# Text
with text, we can describe something, convey information, and so on<br><br>
creating text using a constructor class is very easy:
```kt 
import io.github.xerooup.blitzy.graphics.objects.Text
import io.github.xerooup.blitzy.graphics.objects.Font

Text("!dlroW ,olleH", Color)
```
and create text in game:
```kt 
private lateinit var font: Font

override fun create() {
    font = Font("PATH/FROM/RESOURCES", FONT_SIZE)
}

override fun render(draw: DrawContext) {
    draw.text(
        Text("Hello, World!", Color),
        font,
        X, Y
    )
}
```