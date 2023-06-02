import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.window.ComposeUIViewController
import com.kiliansteenman.kemulation.common.Keyboard

fun MainViewController() = ComposeUIViewController {
    Column {
        Text(text = "KEmulation")
        Keyboard { key, isPressed -> }
    }
}