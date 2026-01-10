# Mouse input
mouse input - a system that processes mouse button clicks or obtains the current cursor position and converts them into actions in the game (for example, clicking a button).<br><br>
let's not write the full code, but instead show an example of its usage in the **update** function right away:
```kt 
import org.xeroup.blitzy.input.MouseInput
import org.xeroup.blitzy.input.MouseInput.Buttons

override fun update(delta: Float) {
    // buttons list: Buttons.MIDDLE, Buttons.RIGHT, Buttons.LEFT
    // check if mouse button pressed
    if (MouseInput.isButtonPressed(Buttons.ANY_BUTTON)) {
        // your code
    }
    
    // returns true only on the frame when button was pressed
    if (MouseInput.isButtonJustPressed(Buttons.ANY_BUTTON)) {
        // your code
    }
    
    // get last cursor x or y
    val mouseX: Int = MouseInput.getX()
    val mouseY: Int = MouseInput.getY()
}
```