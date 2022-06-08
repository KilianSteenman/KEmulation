@file:OptIn(ExperimentalTime::class)

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.Display
import kotlinx.coroutines.delay
import java.io.File
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

fun main() = application {
    val display = Display(64, 32)
    val cpu = Cpu(display = display)
    cpu.loadProgram(loadRom("/Users/kilian/_workspace/games/AGBE/CHIP-8/IBM_Logo.ch8"))

    Window(onCloseRequest = ::exitApplication) {
        var ticks by remember { mutableStateOf(0) }
        var pixels by remember { mutableStateOf(emptyList<Offset>()) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(((1f / 60) * 1000).toLong())
                cpu.executeProgram()
                pixels = display.enabledPixels
                ticks++
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawPoints(points = pixels, pointMode = PointMode.Points, color = Color.Blue, strokeWidth = 10f)
        }
    }
}

val Display.enabledPixels: List<Offset>
    get() = pixels.mapIndexed { index, isEnabled ->
        Pair(index.toOffset(), isEnabled)
    }
        .filter { it.second }
        .map { it.first }
        .toList()

fun Int.toOffset(): Offset {
    val x = (this / 64) * 10 + 10
    val y = this % 64 * 10 + 10
    return Offset(y.toFloat(), x.toFloat())
}

fun loadRom(path: String): ByteArray {
    return File(path).readBytes()
}