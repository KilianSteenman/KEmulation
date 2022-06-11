package com.kiliansteenman.kemulation.chip8

class Input {
    private val keyState = BooleanArray(0xF) { false }

    fun isKeyPressed(keyIndex: UByte): Boolean {
        return keyState[keyIndex.toInt()]
    }

    fun setKeyPressed(keyIndex: Int, isPressed: Boolean) {
        keyState[keyIndex] = isPressed
    }
}