# Camera
using the camera class we can bind the screen camera to any entity<br><br>
let's learn how to create, control, and render a camera:
```kt 
import io.github.xerooup.blitzy.core.camera.Camera

class MyGame : Game() {
    private lateinit var camera: Camera

    override fun create() {
        camera = Camera(WINDOW_WIDTH, WINDOW_HEIGHT)
        
        camera.follow(ENTITY) // bind the camera to an entity
        // we can unbind the camera: camera.unfollow()
    }

    override fun update() {
        camera.shake(POWER, TIME)
        camera.update(delta)
    }

    override fun render(draw: DrawContext) {
        // camera settings

        // camera zoom
        // the smaller, the farther the camera
        // the larger, the closer the camera
        camera.zoom = 1f
        
        // camera smoothing
        // the smaller, the smoother the camera
        // the larger, the more shaking
        camera.smoothing = 0.05f
        
        draw.camera(camera)
        
        // use draw.resetCamera() so that after the call we can attach objects to the camera:
        draw.resetCamera()
        
        // create the object(-s)
        draw.text(TEXT, FONT, 20, 20)
        // now the text will always be at the edge of the screen, regardless of how far the camera is from
    }
}
```