import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import com.kiliansteenman.kemulation.chip8.Pixels

@Composable
fun MonochromeDisplay(
    pixels: Pixels,
    pixelColor: Color = Color.Black,
) {
    Canvas(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        drawPoints(points = pixels.pixels.toOffsets(), pointMode = PointMode.Points, color = pixelColor, strokeWidth = 10f)
    }
}

private fun List<Boolean>.toOffsets(): List<Offset> {
    return this.mapIndexed { index, isEnabled ->
        Pair(index.toOffset(), isEnabled)
    }.filter { it.second }
        .map { it.first }
}

fun Int.toOffset(): Offset {
    val x = (this / 64) * 10 + 10
    val y = this % 64 * 10 + 10
    return Offset(y.toFloat(), x.toFloat())
}