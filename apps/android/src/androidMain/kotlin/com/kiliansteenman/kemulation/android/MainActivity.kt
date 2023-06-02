@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulation.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kiliansteenman.kemulation.chip8.Chip8
import com.kiliansteenman.kemulation.chip8.InputState
import com.kiliansteenman.kemulation.common.KEmulationApp
import com.kiliansteenman.kemulation.common.OnScreenKeyboard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onScreenKeyboard = OnScreenKeyboard(InputState())
        val chip8 = Chip8(input = onScreenKeyboard)

        setContent {
            var isRunning by remember { mutableStateOf(false) }

            val romSelectLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri ->
                if (uri != null) {
                    contentResolver.openInputStream(uri)?.buffered()?.use {
                        val romFile = it.readBytes().toUByteArray()
                        chip8.loadRom(romFile)
                        chip8.start()
                        isRunning = true
                    }
                }
            }

            KEmulationApp(chip8, onScreenKeyboard, isRunning) { romSelectLauncher.launch("*/*") }
        }
    }
}
