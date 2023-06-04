package com.kiliansteenman.kemulation.chip8

import java.awt.Toolkit

actual class Audio {

    actual fun play(beepLength: Int) {
        Toolkit.getDefaultToolkit().beep()
    }

    actual fun stop() {
        // Nothing to do here
    }
}