@file:OptIn(ExperimentalUnsignedTypes::class, ExperimentalUnsignedTypes::class)

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.kiliansteenman.kemulation.chip8.Chip8
import com.kiliansteenman.kemulation.chip8.InputState
import com.kiliansteenman.kemulation.common.KEmulationApp
import com.kiliansteenman.kemulation.common.OnScreenKeyboard
import java.awt.FileDialog
import java.io.File

fun main(args: Array<String>) {
    val romPath = args.getOrNull(0)
    val rom = if (romPath != null) loadRom(romPath) else null

    val keyboardInput = KeyboardInput()
    val onScreenKeyboard = OnScreenKeyboard(InputState())
    val chip8 = Chip8(input = keyboardInput)

    if (rom != null) {
        chip8.apply {
            loadRom(rom)
            start()
        }
    }

    return singleWindowApplication(
        state = WindowState(size = DpSize(330.dp, 600.dp)),
        onKeyEvent = keyboardInput::processKeyEvent
    ) {
        var isRunning by remember { mutableStateOf(false) }

        KEmulationApp(chip8, onScreenKeyboard, isRunning) {
            showFileDialog(window) { romFile ->
                chip8.loadRom(romFile)
                chip8.start()
                isRunning = true
            }
        }
    }
}

private fun showFileDialog(window: ComposeWindow, onFileSelected: (UByteArray) -> Unit) {
    FileDialog(window).apply {
        isVisible = true
        onFileSelected(loadRom(File(directory, file).absolutePath))
    }
}

fun loadRom(path: String): UByteArray {
    return File(path).readBytes().toUByteArray()
}