# Getting started
welcome to blitzy documentation!<br>
below is the code with comments that will help you understand the basics of the engine
<br><br>
```kt
import io.github.xerooup.blitzy.core.Game
import io.github.xerooup.blitzy.core.Settings
import io.github.xerooup.blitzy.graphics.Color
import io.github.xerooup.blitzy.graphics.DrawContext

// main game class
// within the main class reside the core functions of the game.
class MyGame : Game() {
    override fun settings(settings: Settings) {
        // function for setting your game window
        settings.width = 800 
        settings.height = 600
        settings.title = "My Game"
        settings.targetFPS = 60 // frame rate limitation
        settings.background = Color.BLACK
        settings.iconPath = null
        // (all pathes in blitzy are relative to the 'resources' folder)
        settings.decorated = true // if false: borderless window (no close/minimize buttons)
        settings.windowX = null // null - default
        settings.windowY = null 
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
    Game.run(MyGame())
}
// also, using Game.fps we can get fps
// and Game.uptime to get game uptime
```
<br>
when you run this code, a white square should appear in the middle.

