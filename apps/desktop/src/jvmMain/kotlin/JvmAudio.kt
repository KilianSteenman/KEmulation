import com.kiliansteenman.kemulation.chip8.Audio
import java.awt.Toolkit

internal class JvmAudio : Audio() {

    override fun play(beepLength: Int) {
        Toolkit.getDefaultToolkit().beep()
    }

    override fun stop() {
        // Nothing to do here
    }
}