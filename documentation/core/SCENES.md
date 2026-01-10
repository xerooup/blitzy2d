# Scene management
scene management - a convenient feature. you create a scene (for example, a menu or a level) once and can then quickly switch between them without having to load everything from scratch.<br><br>
however, if you use the SceneManager, after switching from the Game class to another scene, you will not be able to return to the Game class, as it is not a scene. but you can implement the game logic in another scene, to which you can always return.<br><br>
let's examine an example of using scenes:
```kt
import org.xeroup.blitzy.core.Game
import org.xeroup.blitzy.core.Scene
import org.xeroup.blitzy.core.SceneManager
import org.xeroup.blitzy.core.Settings
import org.xeroup.blitzy.graphics.Color
import org.xeroup.blitzy.graphics.DrawContext
import org.xeroup.blitzy.input.Input
import org.xeroup.blitzy.input.Input.Keys

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
        // initialize and register all game scenes with the scene manager
        sceneManager.addScene("first", Scene1(sceneManager))
        sceneManager.addScene("second", Scene2(sceneManager))

        // start the game with the first scene
        sceneManager.setScene("first")
    }

    override fun update(delta: Float) {
        // update current scene
        sceneManager.update(delta)
    }

    override fun render(draw: DrawContext) {
        // render current scene
        sceneManager.render(draw)
    }
}

// first scene
class Scene1(private val sceneManager: SceneManager) : Scene() {
    override fun update(delta: Float) {
        // check if enter pressed switch scene to second
        if (Input.isKeyPressed(Keys.ENTER)) {
            // switch to the second scene
            sceneManager.setScene("second")
        }
    }

    override fun render(draw: DrawContext) {
        draw.fillRect(350, 250, 100, 100, Color.WHITE)
    }
}

// second scene
class Scene2(private val sceneManager: SceneManager) : Scene() {
    override fun update(delta: Float) {
        // check if escape key is pressed to return to first scen
        if (Input.isKeyPressed(Keys.ESCAPE)) {
            // switch back to the first scene
            sceneManager.setScene("first")
        }
    }

    override fun render(draw: DrawContext) {
        draw.fillRect(300, 200, 100, 100, Color.WHITE)
    }
}

fun main() {
    // run your game
    Game.run(MyGame())
}
```
<br>
when you run the code, you can switch between scenes. To switch to the second scene, press ENTER, and to return, press ESC.