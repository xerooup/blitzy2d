# Entity classes
think of a game scene as a room. entity classes are all the things you can put in that room: the player, an enemy, a treasure chest, a flying bullet.<br><br>
let's examine an example of using entities:
```kt
import org.xeroup.blitzy.core.Game
import org.xeroup.blitzy.core.Settings
import org.xeroup.blitzy.entity.Entity
import org.xeroup.blitzy.graphics.Color
import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.input.Input
import org.xeroup.blitzy.input.Input.Keys

class MyGame : Game() {
    val player = Player()

    override fun settings(settings: Settings) {
        settings.width = 800
        settings.height = 600
        settings.title = "My Game"
        settings.background = Color.BLACK
    }

    override fun create() {
        player.create() // init your entity
    }

    override fun update(delta: Float) {
        player.update(delta) // update your entity
    }

    override fun render(draw: DrawContext) {
        player.render(draw) // render your entity
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

    // update entity
    override fun update(delta: Float) {
        // movement
        if (Input.isKeyPressed(Keys.W)) y -= speed * delta
        if (Input.isKeyPressed(Keys.S)) y += speed * delta
        if (Input.isKeyPressed(Keys.A)) x -= speed * delta
        if (Input.isKeyPressed(Keys.D)) x += speed * delta
    }

    // render entity
    override fun render(draw: DrawContext) {
        // if you have texture:
        // draw.texture(Texture("PATH"), x, y)
        draw.fillRect(x.toInt(), y.toInt(), width.toInt(), height.toInt(), Color.WHITE)
    }
}

fun main() {
    // run your game
    Game.run(MyGame())
}
```
<br>
when you run the code, an entity will appear in front of you (if you haven't set a texture, it will be just a white square) that is controlled using the WASD keys.

