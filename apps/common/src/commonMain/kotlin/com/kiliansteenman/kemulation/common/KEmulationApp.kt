package com.kiliansteenman.kemulation.common

import androidx.compose.runtime.Composable
import com.kiliansteenman.kemulation.chip8.Chip8

@Composable
fun KEmulationApp(
    chip8: Chip8,
    onScreenKeyboard: OnScreenKeyboard,
    isRunning: Boolean,
    onSelectRomClicked: () -> Unit,
) {
    if (!isRunning) {
        RomSelectScreen(onSelectRomClicked)
    } else {
        Chip8Screen(chip8, onScreenKeyboard)
    }
}