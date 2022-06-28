package com.kiliansteenman.kemulation.chip8

expect class Audio() {

    fun play(beepLength: Int)

    fun stop()
}