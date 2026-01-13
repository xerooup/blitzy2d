# Getting started
welcome to blitzy documentation!<br>
below is the code with comments that will help you understand the basics of the engine
<br><br>
```kt
import org.xeroup.blitzy.core.Game
import org.xeroup.blitzy.core.Settings
import org.xeroup.blitzy.graphics.Color
import org.xeroup.blitzy.graphics.DrawContext

// main game class
// within the main class reside the core functions of the game.
class MyGame : Game() {
    override fun settings(settings: Settings) {
        // function for setting your game window
        settings.width = 800 // window width
        settings.height = 600 // height
        settings.title = "My Game" // window title
        settings.targetFPS = 60 // frame rate limitation
        settings.background = Color.BLACK // window background color (or: Color(0x000000))
        settings.iconPath = null // path to your icon for window
        // (all pathes in blitzy are relative to the 'resources' folder)
        settings.decorated = true // if false: borderless window (no close/minimize buttons)
        settings.windowX = null // x coordinate of your window (null - default)
        settings.windowY = null // y coordinate
    }
    
    override fun create() {
        // function for init your entities, textures, and so on
    }
    override fun update(delta: Float) {
        // function that updates your scene each frame
    }
    override fun render(draw: DrawContext) {
        // function for render your objects
        // let's draw a filled rectangle:
        draw.fillRect(350, 250, 100, 100, Color.WHITE)
    }
}

fun main() {
    // run your game
    Game.run(MyGame())
}
// also, using Game.fps we can get fps
```
<br>
when you run this code, a white square should appear in the middle.

