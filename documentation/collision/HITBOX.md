# Hitboxes and collisions
using hitboxes, we can set boundaries for entities and objects, thereby allowing us to check for collisions with other objects or entities.<br><br>
let's examine an example of using hitboxes:
```kt
import org.xeroup.blitzy.collision.Hitbox
import org.xeroup.blitzy.core.Game
import org.xeroup.blitzy.core.Settings
import org.xeroup.blitzy.entity.Entity
import org.xeroup.blitzy.graphics.Color
import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.input.Keyboard
import org.xeroup.blitzy.input.Keyboard.Keys

class MyGame : Game() {
    private val player = Player()
    private val walls = listOf(
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
        for (wall in walls) {
            if (player.getBounds().intersects(wall)) {
                val correction = player.getBounds().resolveCollision(wall) // obtain a vector for reflection
                // set player positions
                player.x += correction.x
                player.y += correction.y
                break // stop after first collision
            }
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
        if (Input.isKeyPressed(Keys.W)) y -= speed * delta
        if (Input.isKeyPressed(Keys.S)) y += speed * delta
        if (Input.isKeyPressed(Keys.A)) x -= speed * delta
        if (Input.isKeyPressed(Keys.D)) x += speed * delta
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