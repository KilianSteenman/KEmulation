@file:OptIn(ExperimentalUnsignedTypes::class)

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.kiliansteenman.kemulation.chip8.Chip8
import java.awt.FileDialog
import java.io.File

fun main(args: Array<String>) {
    val romPath = args.getOrNull(0)
    val rom = if (romPath != null) loadRom(romPath) else null

    val keyboardInput = KeyboardInput()
    val chip8 = Chip8(input = keyboardInput)

    if (rom != null) {
        chip8.apply {
            loadRom(rom)
            start()
        }
    }

    return singleWindowApplication(
        state = WindowState(size = DpSize(640.dp, 320.dp)),
        onKeyEvent = keyboardInput::processKeyEvent
    ) {
        var file: UByteArray? by remember { mutableStateOf(rom) }

        if (file == null) {
            FileSelection(window) { selectedFile ->
                file = selectedFile
                chip8.apply {
                    loadRom(selectedFile)
                    start()
                }
            }
        } else {
            Chip8Player(chip8)
        }
    }
}

@Composable
fun FileSelection(window: ComposeWindow, onFileSelected: (UByteArray) -> Unit) {
    Button(onClick = {
        FileDialog(window).apply {
            isVisible = true
            onFileSelected(loadRom(File(directory, file).absolutePath))
        }
    }) {
        Text("Select rom file")
    }
}

@Composable
fun Chip8Player(chip8: Chip8) {
    val pixels = chip8.pixels.collectAsState(null)

    pixels.value?.let {
        MonochromeDisplay(pixels = it.toTypedArray())
    }
}

fun loadRom(path: String): UByteArray {
    return File(path).readBytes().toUByteArray()
}