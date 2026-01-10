package org.xeroup.blitzy.core.internal

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWImage
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer

object IconLoader {
    fun setIcon(window: Long, path: String?) {
        if (path == null) return
        try {
            MemoryStack.stackPush().use { stack ->
                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val comp = stack.mallocInt(1)

                val inputStream = IconLoader::class.java.classLoader.getResourceAsStream(path)
                    ?: throw RuntimeException("icon not found: $path")

                val bytes = inputStream.readAllBytes()
                val buffer: ByteBuffer = BufferUtils.createByteBuffer(bytes.size)
                buffer.put(bytes)
                buffer.flip()

                val pixels = STBImage.stbi_load_from_memory(buffer, w, h, comp, 4)
                    ?: throw RuntimeException("failed to decode icon: ${STBImage.stbi_failure_reason()}")

                try {
                    val icon = GLFWImage.malloc(stack)
                    icon.set(w.get(0), h.get(0), pixels)

                    val icons = GLFWImage.malloc(1, stack)
                    icons.put(0, icon)

                    GLFW.glfwSetWindowIcon(window, icons)
                } finally {
                    STBImage.stbi_image_free(pixels)
                }
            }
        } catch (e: Exception) {
            System.err.println("failed to load icon: ${e.message}")
        }
    }
}