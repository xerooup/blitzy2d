# Camera
using the camera class we can bind the screen camera to any entity<br><br>
let's learn how to create, control, and render a camera:
```kt 
import org.xeroup.blitzy.core.camera.Camera

class MyGame : Game() {
    private lateinit var camera: Camera

    override fun create() {
        // initialize the camera
        camera = Camera(WINDOW_WIDTH, WINDOW_HEIGHT)

        // follow camera
        // now the camera will be bound to the specified entity
        camera.follow(ENTITY)

        // we can unbind the camera: camera.unfollow()
    }

    override fun update() {
        // we can shake the camera:
        camera.shake(POWER, TIME)

        camera.update(delta) // update camera
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
        
        // camera render
        draw.camera(camera)
        
        // use draw.resetCamera() so that after the call we can attach objects to the camera:
        draw.resetCamera()
        
        // create the object(-s)
        draw.text(TEXT, FONT, 20, 20)
        // now the text will always be at the edge of the screen, regardless of how far the camera is from
    }
}
```