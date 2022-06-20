@file:OptIn(ExperimentalTime::class)

import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.CpuState
import com.kiliansteenman.kemulation.chip8.Display
import com.kiliansteenman.kemulation.chip8.InputState
import kotlinx.coroutines.delay
import java.awt.Toolkit
import java.io.File
import kotlin.system.exitProcess
import kotlin.time.ExperimentalTime

fun main(args: Array<String>) {
    val romPath = args.getOrNull(0) ?: exitProcess(0)

    val inputState = InputState()
    val keyboardInput = KeyboardInput(inputState)
    return singleWindowApplication(
        state = WindowState(size = DpSize(640.dp, 320.dp)),
        onKeyEvent = { keyEvent -> keyboardInput.processKeyEvent(keyEvent) }
    ) {
        val cpuState = CpuState()
        val display = Display(64, 32)
        val cpu = Cpu(state = cpuState, display = display, inputState = inputState)
        cpu.loadProgram(loadRom(romPath))

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
            Toolkit.getDefaultToolkit().beep()
        }

        MonochromeDisplay(pixels = pixels)
    }
}

fun loadRom(path: String): UByteArray {
    return File(path).readBytes().toUByteArray()
}