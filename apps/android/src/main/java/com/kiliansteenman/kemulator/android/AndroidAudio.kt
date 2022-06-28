package com.kiliansteenman.kemulator.android

import android.media.AudioManager
import android.media.ToneGenerator
import com.kiliansteenman.kemulation.chip8.Audio

class AndroidAudio : Audio() {

    private var tone: ToneGenerator? = null

    override fun play(beepLength: Int) {
        tone = ToneGenerator(AudioManager.STREAM_MUSIC, 100).apply {
            startTone(ToneGenerator.TONE_CDMA_PIP, 150)
        }
    }

    override fun stop() {
        tone?.stopTone()
        tone = null
    }
}