package com.kiliansteenman.kemulation.android

import com.kiliansteenman.kemulation.chip8.Input
import com.kiliansteenman.kemulation.chip8.InputState

class OnScreenKeyboard(
    override val state: InputState
) : Input() {

    fun setKeyPressed(key: Int, isPressed: Boolean) {
        state.setKeyPressed(key, isPressed)
    }
}