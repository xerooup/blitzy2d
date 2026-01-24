# Hitboxes and collisions
using hitboxes, we can set boundaries for entities and objects, thereby allowing us to check for collisions with other objects or entities.<br><br>
let's examine an example of using hitboxes:
```kt
import io.github.xerooup.blitzy.collision.Hitbox
import io.github.xerooup.blitzy.core.Game
import io.github.xerooup.blitzy.core.Settings
import io.github.xerooup.blitzy.entity.Entity
import io.github.xerooup.blitzy.graphics.Color
import io.github.xerooup.graphics.DrawContext
import io.github.xerooup.input.Keyboard
import io.github.xerooup.input.Keyboard.Keys

class MyGame : Game() {
    private val player = Player()
    private val walls = listOf(
        // (x, y, width, height)
        Hitbox(350f, 50f, 100f, 100f),  // wall 1
        Hitbox(200f, 300f, 150f, 50f),  // wall 2
        Hitbox(500f, 400f, 100f, 80f)   // wall 3
    )

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
        // try to move
        player.update(delta)

        // check collisions with all walls
        val checkWallCollision = player.collidesAny(walls)
        if (checkWallCollision != null) {
            val correction = player.getBounds().resolveCollision(checkWallCollision) // obtain a vector for reflection
            // set player positions
            player.x += correction.x
            player.y += correction.y
        }
    }

    override fun render(draw: DrawContext) {
        player.render(draw)

        // draw all walls
        for (wall in walls) {
            draw.fillRect(
                // we can retrieve the settings of our hitbox
                wall.x.toInt(),
                wall.y.toInt(),
                wall.width.toInt(),
                wall.height.toInt(),
                Color.WHITE
            )
        }
    }
}

class Player : Entity() {
    private val speed = 200f

    override fun create() {
        x = 100f
        y = 100f
        width = 40f
        height = 40f
    }

    override fun update(delta: Float) {
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
when you run the code, three walls and a player will appear on the screen, which you can control. try crashing into a wall to test the hitboxes.