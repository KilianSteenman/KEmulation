@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulation.android

import MonochromeDisplay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import com.kiliansteenman.kemulation.chip8.Chip8
import com.kiliansteenman.kemulation.chip8.InputState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inputStream = resources.openRawResource(R.raw.breakout)
        val rom = ByteArray(inputStream.available())
        inputStream.read(rom)
        inputStream.close()

        setContent {
            Chip8Screen(rom.map { it.toUByte() }.toUByteArray())
        }
    }
}

@Composable
private fun Chip8Screen(rom: UByteArray) {
    val inputState = InputState()
    val chip8 = Chip8(input = OnScreenKeyboard(inputState)).apply {
        loadRom(rom)
        start()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pixels = chip8.pixels.collectAsState(null)

        pixels.value?.let {
            MonochromeDisplay(pixels = it.toTypedArray())
        }
        Keyboard { key, isPressed -> inputState.setKeyPressed(key, isPressed) }
    }
}
