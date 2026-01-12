# Keyboard input
keyboard input - the system that processes key presses on the keyboard and converts them into actions in the game (for example, character movement).<br><br>
let's not write the full code, but instead show an example of its usage in the **update** function right away:
```kt 
import org.xeroup.blitzy.input.Keyboard
import org.xeroup.blitzy.input.Keyboard.Keys

overide fun update(delta: Float) {
    // check if key pressed
    if (Keyboard.isKeyPressed(Keys.ANY_KEY)) {
        // your code
    }
    
    // check if key released
    if (Keyboard.isKeyReleased(Keys.ANY_KEY)) {
        // your code
    }
}
```