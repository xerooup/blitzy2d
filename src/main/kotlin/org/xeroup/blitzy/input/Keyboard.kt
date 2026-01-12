package org.xeroup.blitzy.input

import org.lwjgl.glfw.GLFW

object Keyboard {
    private var windowHandle = 0L

    internal fun init(window: Long) {
        windowHandle = window
    }

    // main method
    fun isKeyPressed(keyCode: Int): Boolean {
        return GLFW.glfwGetKey(windowHandle, keyCode) == GLFW.GLFW_PRESS
    }

    fun isKeyReleased(keyCode: Int): Boolean {
        return GLFW.glfwGetKey(windowHandle, keyCode) == GLFW.GLFW_RELEASE
    }

    object Keys {
        // letters
        const val A = GLFW.GLFW_KEY_A
        const val B = GLFW.GLFW_KEY_B
        const val C = GLFW.GLFW_KEY_C
        const val D = GLFW.GLFW_KEY_D
        const val E = GLFW.GLFW_KEY_E
        const val F = GLFW.GLFW_KEY_F
        const val G = GLFW.GLFW_KEY_G
        const val H = GLFW.GLFW_KEY_H
        const val I = GLFW.GLFW_KEY_I
        const val J = GLFW.GLFW_KEY_J
        const val K = GLFW.GLFW_KEY_K
        const val L = GLFW.GLFW_KEY_L
        const val M = GLFW.GLFW_KEY_M
        const val N = GLFW.GLFW_KEY_N
        const val O = GLFW.GLFW_KEY_O
        const val P = GLFW.GLFW_KEY_P
        const val Q = GLFW.GLFW_KEY_Q
        const val R = GLFW.GLFW_KEY_R
        const val S = GLFW.GLFW_KEY_S
        const val T = GLFW.GLFW_KEY_T
        const val U = GLFW.GLFW_KEY_U
        const val V = GLFW.GLFW_KEY_V
        const val W = GLFW.GLFW_KEY_W
        const val X = GLFW.GLFW_KEY_X
        const val Y = GLFW.GLFW_KEY_Y
        const val Z = GLFW.GLFW_KEY_Z

        // numbers
        const val NUM_0 = GLFW.GLFW_KEY_0
        const val NUM_1 = GLFW.GLFW_KEY_1
        const val NUM_2 = GLFW.GLFW_KEY_2
        const val NUM_3 = GLFW.GLFW_KEY_3
        const val NUM_4 = GLFW.GLFW_KEY_4
        const val NUM_5 = GLFW.GLFW_KEY_5
        const val NUM_6 = GLFW.GLFW_KEY_6
        const val NUM_7 = GLFW.GLFW_KEY_7
        const val NUM_8 = GLFW.GLFW_KEY_8
        const val NUM_9 = GLFW.GLFW_KEY_9

        // special keys
        const val SPACE = GLFW.GLFW_KEY_SPACE
        const val ENTER = GLFW.GLFW_KEY_ENTER
        const val ESCAPE = GLFW.GLFW_KEY_ESCAPE
        const val SHIFT = GLFW.GLFW_KEY_LEFT_SHIFT
        const val CTRL = GLFW.GLFW_KEY_LEFT_CONTROL
        const val ALT = GLFW.GLFW_KEY_LEFT_ALT
        const val TAB = GLFW.GLFW_KEY_TAB
        const val BACKSPACE = GLFW.GLFW_KEY_BACKSPACE

        // arrows
        const val UP = GLFW.GLFW_KEY_UP
        const val DOWN = GLFW.GLFW_KEY_DOWN
        const val LEFT = GLFW.GLFW_KEY_LEFT
        const val RIGHT = GLFW.GLFW_KEY_RIGHT

        // function keys
        const val F1 = GLFW.GLFW_KEY_F1
        const val F2 = GLFW.GLFW_KEY_F2
        const val F3 = GLFW.GLFW_KEY_F3
        const val F4 = GLFW.GLFW_KEY_F4
        const val F5 = GLFW.GLFW_KEY_F5
        const val F6 = GLFW.GLFW_KEY_F6
        const val F7 = GLFW.GLFW_KEY_F7
        const val F8 = GLFW.GLFW_KEY_F8
        const val F9 = GLFW.GLFW_KEY_F9
        const val F10 = GLFW.GLFW_KEY_F10
        const val F11 = GLFW.GLFW_KEY_F11
        const val F12 = GLFW.GLFW_KEY_F12
    }

}