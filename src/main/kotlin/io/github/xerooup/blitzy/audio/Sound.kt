package io.github.xerooup.blitzy.audio

import org.lwjgl.openal.AL10
import org.lwjgl.system.MemoryUtil
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem

class Sound(private val path: String) {
    private var bufferId = 0
    private var sourceId = 0
    private var isLooping = false
    private var individualVolume: Float = 1.0f

    init {
        // ensure audio system is initialized
        if (!AudioManager.initialized) {
            AudioManager.init()
        }
        loadWav()
        AudioManager.registerSound(this)
    }

    // loads wav file from resources and creates openal buffer and source
    private fun loadWav() {
        val inputStream: InputStream = javaClass.classLoader.getResourceAsStream(path)
            ?: throw RuntimeException("sound file not found: $path")

        // read all bytes into memory first
        val audioBytes = inputStream.readAllBytes()
        val byteArrayInputStream = ByteArrayInputStream(audioBytes)

        try {
            val audioStream: AudioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream)
            val format: AudioFormat = audioStream.format

            // check if format is pcm (required for OpenAL)
            if (format.encoding != AudioFormat.Encoding.PCM_SIGNED &&
                format.encoding != AudioFormat.Encoding.PCM_UNSIGNED) {
                // try to convert to pcm format
                val targetFormat = AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    format.sampleRate,
                    16,
                    format.channels,
                    format.channels * 2,
                    format.sampleRate,
                    false
                )
                val convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream)
                loadConvertedWav(convertedStream)
                convertedStream.close()
            } else {
                loadConvertedWav(audioStream)
            }

            audioStream.close()
            byteArrayInputStream.close()
            inputStream.close()

        } catch (e: Exception) {
            throw RuntimeException("failed to load audio: ${e.message}")
        }
    }

    private fun loadConvertedWav(audioStream: AudioInputStream) {
        val format = audioStream.format

        // read all audio data
        val buffer = ByteArray(4096)
        val data = ByteArrayOutputStream()

        var bytesRead: Int
        while (audioStream.read(buffer).also { bytesRead = it } != -1) {
            data.write(buffer, 0, bytesRead)
        }

        val audioData = data.toByteArray()
        val byteBuffer = MemoryUtil.memAlloc(audioData.size)
        byteBuffer.put(audioData)
        byteBuffer.flip()

        // determine OpenAL format based on audio properties
        val alFormat = when {
            format.channels == 1 && format.sampleSizeInBits == 8 -> AL10.AL_FORMAT_MONO8
            format.channels == 1 && format.sampleSizeInBits == 16 -> AL10.AL_FORMAT_MONO16
            format.channels == 2 && format.sampleSizeInBits == 8 -> AL10.AL_FORMAT_STEREO8
            format.channels == 2 && format.sampleSizeInBits == 16 -> AL10.AL_FORMAT_STEREO16
            else -> throw RuntimeException("unsupported audio format: ${format.channels}ch ${format.sampleSizeInBits}bit")
        }

        // create OpenAL buffer and upload data
        bufferId = AL10.alGenBuffers()
        AL10.alBufferData(bufferId, alFormat, byteBuffer, format.sampleRate.toInt())
        MemoryUtil.memFree(byteBuffer)

        // create OpenAL source and attach buffer
        sourceId = AL10.alGenSources()
        AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId)
        updateVolume()
    }

    // updates volume based on individual and master volume
    internal fun updateVolume() {
        AL10.alSourcef(sourceId, AL10.AL_GAIN, individualVolume * AudioManager.getVolumeMultiplier())
    }

    // returns openal source id (internal use)
    internal fun getSourceId(): Int = sourceId

    // plays the sound once
    fun play() {
        if (isLooping) {
            AL10.alSourcei(sourceId, AL10.AL_LOOPING, AL10.AL_FALSE)
            isLooping = false
        }
        AL10.alSourcePlay(sourceId)
    }

    // plays the sound in loop
    fun loop() {
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, AL10.AL_TRUE)
        isLooping = true
        AL10.alSourcePlay(sourceId)
    }

    // stops playback
    fun stop() {
        AL10.alSourceStop(sourceId)
    }

    // pauses playback
    fun pause() {
        AL10.alSourcePause(sourceId)
    }

    // resumes paused playback
    fun resume() {
        if (AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PAUSED) {
            AL10.alSourcePlay(sourceId)
        }
    }

    // sets individual volume (0.0 to 1.0)
    fun setVolume(volume: Float) {
        individualVolume = volume.coerceIn(0f, 1f)
        updateVolume()
    }

    // checks if sound is currently playing
    fun isPlaying(): Boolean {
        return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING
    }

    // cleans up openal resources
    fun dispose() {
        stop()
        AudioManager.unregisterSound(this)
        AL10.alDeleteSources(sourceId)
        AL10.alDeleteBuffers(bufferId)
    }
}