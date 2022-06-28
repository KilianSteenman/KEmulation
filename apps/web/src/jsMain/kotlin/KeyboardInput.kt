import com.kiliansteenman.kemulation.chip8.Input
import com.kiliansteenman.kemulation.chip8.InputState
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.KeyboardEvent

internal class KeyboardInput(
    body: HTMLElement
) : Input() {

    override val state = InputState()

    init {
        body.addEventListener("keyup", {
            processKeyboardEvent((it as KeyboardEvent), false)
        })

        body.addEventListener("keydown", {
            processKeyboardEvent((it as KeyboardEvent), true)
        })
    }

    private fun processKeyboardEvent(event: KeyboardEvent, isPressed: Boolean) {
        keyMap[event.keyCode]?.let { keyIndex ->
            state.setKeyPressed(keyIndex, isPressed)
        }
    }

    private val keyMap = mapOf(
        49 to 0x1,
        50 to 0x2,
        51 to 0x3,
        52 to 0xC,
        81 to 0x4,
        87 to 0x5,
        69 to 0x6,
        82 to 0xD,
        65 to 0x7,
        83 to 0x8,
        68 to 0x9,
        70 to 0xE,
        90 to 0xA,
        88 to 0x0,
        67 to 0xB,
        86 to 0xF,
    )
}