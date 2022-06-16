import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode

@Composable
internal fun MonochromeDisplay(pixels: List<Offset>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPoints(points = pixels, pointMode = PointMode.Points, color = Color.Blue, strokeWidth = 10f)
    }
}
