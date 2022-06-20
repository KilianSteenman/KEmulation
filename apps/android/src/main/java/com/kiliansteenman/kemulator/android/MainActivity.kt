@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulator.android

import MonochromeDisplay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
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
//    val keyboardInput = KeyboardInput(inputState)
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
//        Toolkit.getDefaultToolkit().beep()
    }

    MonochromeDisplay(pixels = pixels)
}