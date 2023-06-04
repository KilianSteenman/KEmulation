package com.kiliansteenman.kemulation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
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