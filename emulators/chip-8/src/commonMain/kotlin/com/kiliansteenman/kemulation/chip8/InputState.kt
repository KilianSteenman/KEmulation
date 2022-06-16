package com.kiliansteenman.kemulation.chip8

class InputState {
    private val keyState = BooleanArray(16) { false }

    fun isKeyPressed(keyIndex: UByte): Boolean {
        return keyState[keyIndex.toInt()]
    }

    fun setKeyPressed(keyIndex: Int, isPressed: Boolean) {
        keyState[keyIndex] = isPressed
    }

    fun getPressedKey(): Pair<Boolean, UByte> {
        return if (keyState.contains(true)) {
            Pair(true, keyState.indexOfFirst { it }.toUByte())
        } else {
            Pair(false, 0.toUByte())
        }
    }
}