@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulation.android

import MonochromeDisplay
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.kiliansteenman.kemulation.chip8.Chip8
import com.kiliansteenman.kemulation.chip8.InputState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onScreenKeyboard = OnScreenKeyboard(InputState())
        val chip8 = Chip8(input = onScreenKeyboard)

        setContent {
            var file: UByteArray? by remember { mutableStateOf(null) }

            if (file == null) {
                RomSelectScreen(LocalContext.current) {
                    file = it
                    chip8.loadRom(it)
                    chip8.start()
                }
            } else {
                Chip8Screen(chip8, onScreenKeyboard)
            }
        }
    }
}

@Composable
private fun RomSelectScreen(context: Context, onRomSelected: (rom: UByteArray) -> Unit) {
    val romSelectLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.openInputStream(uri)?.buffered()?.use {
                onRomSelected(it.readBytes().toUByteArray())
            }
        }
    }

    Button(onClick = { romSelectLauncher.launch("*/*") }) {
        Text("Select Chip-8 ROM")
    }
}

@Composable
private fun Chip8Screen(chip8: Chip8, onScreenKeyboard: OnScreenKeyboard) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pixels = chip8.pixels.collectAsState(null)

        pixels.value?.let {
            MonochromeDisplay(pixels = it.toTypedArray())
        }
        Keyboard { key, isPressed -> onScreenKeyboard.setKeyPressed(key, isPressed) }
    }
}
