import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import com.kiliansteenman.kemulation.chip8.Input
import com.kiliansteenman.kemulation.chip8.InputState

class KeyboardInput : Input() {

    override val state: InputState = InputState()

    fun processKeyEvent(keyEvent: KeyEvent): Boolean {
        keyMap[keyEvent.key]?.let { keyIndex ->
            state.setKeyPressed(keyIndex, keyEvent.type == KeyEventType.KeyDown)
            return true
        }
        return false
    }

    @OptIn(ExperimentalComposeUiApi::class)
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
}