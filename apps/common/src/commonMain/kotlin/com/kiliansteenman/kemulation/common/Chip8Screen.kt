package com.kiliansteenman.kemulation.common

import MonochromeDisplay
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import com.kiliansteenman.kemulation.chip8.Chip8
import com.kiliansteenman.kemulation.chip8.Pixels

@Composable
fun Chip8Screen(chip8: Chip8, onScreenKeyboard: OnScreenKeyboard) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pixels = chip8.pixels.collectAsState(Pixels(emptyList()))

        MonochromeDisplay(pixels = pixels.value)
        Keyboard { key, isPressed -> onScreenKeyboard.setKeyPressed(key, isPressed) }
    }
}