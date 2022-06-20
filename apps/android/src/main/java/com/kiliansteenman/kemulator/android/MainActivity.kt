@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulator.android

import MonochromeDisplay
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
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

    var ticks by remember { mutableStateOf(0) }
    var pixels by remember { mutableStateOf(emptyArray<Boolean>()) }
    var playAudio by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(((1f / 60) * 100).toLong())
            cpu.executeProgram()
            pixels = display.pixels.toTypedArray()
            playAudio = cpuState.soundTimer > 0.toUByte()
            ticks++
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

@Composable
private fun Keyboard(onKeyPressed: (key: Int, isPressed: Boolean) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Key("1") { onKeyPressed(0x1, it) }
            Key("2") { onKeyPressed(0x2, it) }
            Key("3") { onKeyPressed(0x3, it) }
            Key("C") { onKeyPressed(0xC, it) }
        }
        Row {
            Key("4") { onKeyPressed(0x4, it) }
            Key("5") { onKeyPressed(0x5, it) }
            Key("6") { onKeyPressed(0x6, it) }
            Key("D") { onKeyPressed(0xD, it) }
        }
        Row {
            Key("7") { onKeyPressed(0x7, it) }
            Key("8") { onKeyPressed(0x8, it) }
            Key("9") { onKeyPressed(0x9, it) }
            Key("E") { onKeyPressed(0xE, it) }
        }
        Row {
            Key("A") { onKeyPressed(0xA, it) }
            Key("0") { onKeyPressed(0x0, it) }
            Key("B") { onKeyPressed(0xB, it) }
            Key("F") { onKeyPressed(0xF, it) }
        }
    }
}

@Composable
private fun Key(key: String, onPress: (isPressed: Boolean) -> Unit) {
    Box(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    try {
                        onPress(true)
                        awaitRelease()
                    } finally {
                        onPress(false)
                    }
                },
            )
        }
        .size(80.dp)
    ) {
        Text(
            text = key,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}