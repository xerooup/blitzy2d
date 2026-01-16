# AudioManager
with AudioManager, we gain more capabilities for working with sounds<br><br>
let's look at the methods of the AudioManager:
```kt 
import org.xeroup.blitzy.audio.Sound
import org.xeroup.blitzy.audio.AudioManager

// changing the state of sounds
AudioManager.pauseAll()
AudioManager.resumeAll()
AudioManager.stopAll()

// sounds settings
AudioManager.masterVolume = VOLUME
// .masterVolume() - global volume multiplier for all sounds (0.0f to 1.0f). 
// sets overall game volume

// checking the system state
AudioManager.initialized // if initialized - true, if stopped - false

// forced stop
AudioManager.dispose() 
// be careful!
// after use, all sounds are stopped and cleared.
// you can reinitialize the system via AudioManager.init().


```