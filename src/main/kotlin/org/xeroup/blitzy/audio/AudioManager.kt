package org.xeroup.blitzy.audio

import org.lwjgl.openal.AL
import org.lwjgl.openal.ALC
import org.lwjgl.openal.ALC10
import org.lwjgl.openal.AL10
import java.nio.ByteBuffer
import java.nio.IntBuffer

object AudioManager {
    private var device: Long = 0
    private var context: Long = 0
    var initialized = false
    private val sounds = mutableListOf<Sound>()

    // global volume control (0.0 to 1.0)
    var masterVolume: Float = 1.0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            // update volume for all registered sounds
            sounds.forEach { it.updateVolume() }
        }

    // initializes openal audio system
    fun init() {
        if (initialized) return

        // open default audio device
        device = ALC10.alcOpenDevice(null as ByteBuffer?)
            ?: throw IllegalStateException("failed to open OpenAL device")

        // create audio context
        context = ALC10.alcCreateContext(device, null as IntBuffer?)
        ALC10.alcMakeContextCurrent(context)

        // create openal capabilities
        AL.createCapabilities(ALC.createCapabilities(device))
        initialized = true
    }

    // registers a sound for global management
    internal fun registerSound(sound: Sound) {
        sounds.add(sound)
    }

    // unregisters a sound
    internal fun unregisterSound(sound: Sound) {
        sounds.remove(sound)
    }

    // stops all playing sounds
    fun stopAll() {
        sounds.forEach { it.stop() }
    }

    // pauses all playing sounds
    fun pauseAll() {
        sounds.forEach { if (it.isPlaying()) AL10.alSourcePause(it.getSourceId()) }
    }

    // resumes all paused sounds
    fun resumeAll() {
        sounds.forEach { if (AL10.alGetSourcei(it.getSourceId(), AL10.AL_SOURCE_STATE) == AL10.AL_PAUSED) {
            AL10.alSourcePlay(it.getSourceId())
        } }
    }

    // cleans up all audio resources
    fun dispose() {
        if (!initialized) return
        stopAll()
        sounds.clear()
        ALC10.alcDestroyContext(context)
        ALC10.alcCloseDevice(device)
        initialized = false
    }

    // returns current master volume multiplier
    internal fun getVolumeMultiplier(): Float = masterVolume
}