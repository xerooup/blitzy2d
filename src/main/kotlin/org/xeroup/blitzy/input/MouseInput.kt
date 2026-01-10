package org.xeroup.blitzy.input

import org.lwjgl.glfw.GLFW

object MouseInput {
    private var windowHandle = 0L
    private var lastX = 0.0
    private var lastY = 0.0
    private var currentButtonStates = mutableMapOf<Int, Boolean>()
    private var previousButtonStates = mutableMapOf<Int, Boolean>()

    internal fun init(window: Long) {
        windowHandle = window
        GLFW.glfwSetCursorPosCallback(window) { _, x, y ->
            lastX = x
            lastY = y
        }

        // initialize button states
        for (button in listOf(Buttons.LEFT, Buttons.RIGHT, Buttons.MIDDLE)) {
            currentButtonStates[button] = false
            previousButtonStates[button] = false
        }

        GLFW.glfwSetMouseButtonCallback(window) { _, button, action, _ ->
            currentButtonStates[button] = action == GLFW.GLFW_PRESS
        }
    }

    fun getX(): Int = lastX.toInt()
    fun getY(): Int = lastY.toInt()

    fun isButtonPressed(button: Int): Boolean {
        return currentButtonStates[button] ?: false
    }

    // returns true only on the frame when button was pressed
    fun isButtonJustPressed(button: Int): Boolean {
        val current = currentButtonStates[button] ?: false
        val previous = previousButtonStates[button] ?: false
        return current && !previous
    }

    object Buttons {
        const val LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT
        const val RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT
        const val MIDDLE = GLFW.GLFW_MOUSE_BUTTON_MIDDLE
    }

    internal fun update() {
        // save current states as previous for next frame
        for ((button, state) in currentButtonStates) {
            previousButtonStates[button] = state
        }
    }
}