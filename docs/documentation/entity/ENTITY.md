# Entity classes
think of a game scene as a room. entity classes are all the things you can put in that room: the player, an enemy, a treasure chest, a flying bullet.<br><br>
let's examine an example of using entities:
```kt
import io.github.xerooup.blitzy.core.Game
import io.github.xerooup.blitzy.core.Settings
import io.github.xerooup.blitzy.entity.Entity
import io.github.xerooup.blitzy.graphics.Color
import io.github.xerooup.blitzy.graphics.DrawContext
import io.github.xerooup.blitzy.input.Keyboard
import io.github.xerooup.blitzy.input.Keyboard.Keys

class MyGame : Game() {
    val player = Player()

    override fun settings(settings: Settings) {
        settings.width = 800
        settings.height = 600
        settings.title = "My Game"
        settings.background = Color.BLACK
    }

    override fun create() {
        player.create()
    }

    override fun update(delta: Float) {
        player.update(delta)
    }

    override fun render(draw: DrawContext) {
        player.render(draw)
    }
}

class Player : Entity() {
    private val speed = 200f

    override fun create() {
        x = 350f
        y = 250f
        width = 64f
        height = 64f
    }
    
    override fun update(delta: Float) {
        // movement
        if (Keyboard.isKeyPressed(Keys.W)) y -= speed * delta
        if (Keyboard.isKeyPressed(Keys.S)) y += speed * delta
        if (Keyboard.isKeyPressed(Keys.A)) x -= speed * delta
        if (Keyboard.isKeyPressed(Keys.D)) x += speed * delta
    }
    
    override fun render(draw: DrawContext) {
        draw.fillRect(x.toInt(), y.toInt(), width.toInt(), height.toInt(), Color.WHITE)
    }
}

fun main() {
    Game.run(MyGame())
}
```
<br>
when you run the code, an entity will appear in front of you (if you haven't set a texture, it will be just a white square) that is controlled using the WASD keys.

