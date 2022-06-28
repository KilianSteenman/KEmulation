package com.kiliansteenman.kemulation.chip8

import android.media.AudioManager
import android.media.ToneGenerator

actual class Audio {

    private var tone: ToneGenerator? = null

    actual fun play(beepLength: Int) {
        tone = ToneGenerator(AudioManager.STREAM_MUSIC, 100).apply {
            startTone(ToneGenerator.TONE_CDMA_PIP, 150)
        }
    }

    actual fun stop() {
        tone?.stopTone()
        tone = null
    }
}