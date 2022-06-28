package com.kiliansteenman.kemulation.chip8

abstract class Audio {

    abstract fun play(beepLength: Int)

    abstract fun stop()
}