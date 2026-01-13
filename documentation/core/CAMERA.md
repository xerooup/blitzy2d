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
    }
}
```