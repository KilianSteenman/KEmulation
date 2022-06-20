@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulator.android

import MonochromeDisplay
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.CpuState
import com.kiliansteenman.kemulation.chip8.Display
import com.kiliansteenman.kemulation.chip8.InputState
import kotlinx.coroutines.delay

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
    val cpuState = CpuState()
    val display = Display(64, 32)
    val cpu = Cpu(state = cpuState, display = display, inputState = inputState)
    cpu.loadProgram(rom)

    var pixels by remember { mutableStateOf(emptyArray<Boolean>()) }
    var playAudio by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(((1f / 60) * 100).toLong())
            cpu.executeProgram()
            pixels = display.pixels.toTypedArray()
            playAudio = cpuState.soundTimer > 0.toUByte()
        }
    }

    if (playAudio) {
        ToneGenerator(AudioManager.STREAM_MUSIC, 100)
            .startTone(ToneGenerator.TONE_CDMA_PIP, 150)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MonochromeDisplay(pixels = pixels)
        Keyboard { key, isPressed -> inputState.setKeyPressed(key, isPressed) }
    }
}
