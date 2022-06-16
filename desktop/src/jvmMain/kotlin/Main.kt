@file:OptIn(ExperimentalTime::class)

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
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
        var pixels by remember { mutableStateOf(emptyList<Offset>()) }
        var playAudio by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(((1f / 60) * 100).toLong())
                cpu.executeProgram()
                pixels = display.enabledPixels
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

val Display.enabledPixels: List<Offset>
    get() = pixels.mapIndexed { index, isEnabled ->
        Pair(index.toOffset(), isEnabled)
    }.filter { it.second }.map { it.first }.toList()

fun Int.toOffset(): Offset {
    val x = (this / 64) * 10 + 10
    val y = this % 64 * 10 + 10
    return Offset(y.toFloat(), x.toFloat())
}

fun loadRom(path: String): UByteArray {
    return File(path).readBytes().toUByteArray()
}