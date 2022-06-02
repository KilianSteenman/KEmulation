import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.Display

fun main() = application {
    val display = Display(64, 32)
    val cpu = Cpu(display = display)

    display.drawSprite(0, 0, byteArrayOf(0xF0.toByte(), 0x90.toByte(), 0x90.toByte(), 0x90.toByte(), 0xF0.toByte()))

    fun Int.toOffset(): Offset {
        val x = (this / 64) * 10 + 10
        val y = this % 64 * 10 + 10
        return Offset(x.toFloat(), y.toFloat())
    }

    val pixels = display.pixels.mapIndexed { index, isEnabled ->
        Pair(index.toOffset(), isEnabled)
    }
        .filter { it.second }
        .map { it.first }
        .toList()

    Window(onCloseRequest = ::exitApplication) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawPoints(points = pixels, pointMode = PointMode.Points, color = Color.Blue, strokeWidth = 10f)
        }
    }
}
