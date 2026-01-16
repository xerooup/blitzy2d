# Sound
with sounds, we can add auditory feedback, create atmosphere in the game, and enhance the user's gaming experience<br><br>
let's look at the methods of the Sound class:
```kt 
import org.xeroup.blitzy.audio.Sound

// create sound 
val mySound = Sound("PATH/FROM/RESOURCES")

// changing the sound state
mySound.play() // if the sound is looped, it will stop the loop and play only once
mySound.pause()
mySound.resume()
mySound.stop()
mySound.loop()
mySound.isPlaying() // checking if the sound is currently playing (returns true/false)

// sound settings
mySound.setVolume(VOLUME) // from 0.0f to 1.0f

// forced stop
mySound.dispose() // after using .dispose(), you will not be able to control this sound

// DO NOT DO THIS:
Sound("PATH/FROM/RESOURCES").play()
Sound("PATH/FROM/RESOURCES").stop()
// by doing this, you create a new object each time,
// which will prevent you from being able to control it later.
// always save the sound to a variable!
```
<br>
also, check out the section on AUDIO_MANAGER.md, which adds several useful features