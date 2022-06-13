@file:OptIn(ExperimentalTime::class, ExperimentalComposeUiApi::class)

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.Display
import com.kiliansteenman.kemulation.chip8.Input
import kotlinx.coroutines.delay
import java.io.File
import kotlin.time.ExperimentalTime

fun main() {
    val input = Input()
    return singleWindowApplication(
        state = WindowState(size = DpSize(640.dp, 320.dp)),
        onKeyEvent = { keyEvent -> processKeyEvent(keyEvent, input) }
    ) {
        val display = Display(64, 32)
        val cpu = Cpu(display = display, input = input)
        cpu.loadProgram(loadRom("/Users/kilian/_workspace/games/AGBE/CHIP-8/pong.ch8"))

        var ticks by remember { mutableStateOf(0) }
        var pixels by remember { mutableStateOf(emptyList<Offset>()) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(((1f / 60) * 100).toLong())
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

private fun processKeyEvent(keyEvent: KeyEvent, input: Input): Boolean {
    keyMap[keyEvent.key]?.let { keyIndex ->
        input.setKeyPressed(keyIndex, keyEvent.type == KeyEventType.KeyDown)
        return true
    }
    return false
}

private val keyMap = mapOf(
    Key.One to 0x1,
    Key.Two to 0x2,
    Key.Three to 0x3,
    Key.Four to 0xC,
    Key.Q to 0x4,
    Key.W to 0x5,
    Key.E to 0x6,
    Key.R to 0xD,
    Key.A to 0x7,
    Key.S to 0x8,
    Key.D to 0x9,
    Key.F to 0xE,
    Key.Z to 0xA,
    Key.X to 0x0,
    Key.C to 0xB,
    Key.V to 0xF,
)

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

fun loadRom(path: String): UByteArray {
    return File(path).readBytes().toUByteArray()
}