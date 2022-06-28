package com.kiliansteenman.kemulator.android

import com.kiliansteenman.kemulation.chip8.Input
import com.kiliansteenman.kemulation.chip8.InputState

class OnScreenKeyboard(
    override val state: InputState
) : Input()