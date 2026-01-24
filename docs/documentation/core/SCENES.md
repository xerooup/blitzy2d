# Scene management
scene management - a convenient feature. you create a scene (for example, a menu or a level) once and can then quickly switch between them without having to load everything from scratch.<br><br>
however, if you use the SceneManager, after switching from the Game class to another scene, you will not be able to return to the Game class, as it is not a scene. but you can implement the game logic in another scene, to which you can always return.<br><br>
let's examine an example of using scenes:
```kt
import io.github.xerooup.blitzy.core.Game
import io.github.xerooup.blitzy.core.scene.Scene
import io.github.xerooup.blitzy.core.scene.SceneManager
import io.github.xerooup.blitzy.core.Settings
import io.github.xerooup.blitzy.graphics.Color
import io.github.xerooup.blitzy.graphics.DrawContext
import io.github.xerooup.blitzy.input.Keyboard
import io.github.xerooup.blitzy.input.Keyboard.Keys

class MyGame : Game() {
    // create a single scene manager instance for the whole game
    private val sceneManager = SceneManager()

    override fun settings(settings: Settings) {
        settings.width = 800
        settings.height = 600
        settings.title = "My Game"
        settings.background = Color.BLACK
    }

    override fun create() {
        sceneManager.addScene("first", Scene1(sceneManager))
        sceneManager.addScene("second", Scene2(sceneManager))

        // start the game with the first scene
        sceneManager.setScene("first")
    }

    override fun update(delta: Float) {
        sceneManager.update(delta)
    }

    override fun render(draw: DrawContext) {
        sceneManager.render(draw)
    }
}

class Scene1(private val sceneManager: SceneManager) : Scene() {
    override fun update(delta: Float) {
        if (Input.isKeyPressed(Keys.ENTER)) {
            sceneManager.setScene("second")
        }
    }

    override fun render(draw: DrawContext) {
        draw.fillRect(350, 250, 100, 100, Color.WHITE)
    }
}

class Scene2(private val sceneManager: SceneManager) : Scene() {
    override fun update(delta: Float) {
        if (Input.isKeyPressed(Keys.ESCAPE)) {
            sceneManager.setScene("first")
        }
    }

    override fun render(draw: DrawContext) {
        draw.fillRect(300, 200, 100, 100, Color.WHITE)
    }
}

fun main() {
    Game.run(MyGame())
}
```
<br>
when you run the code, you can switch between scenes. To switch to the second scene, press ENTER, and to return, press ESC.