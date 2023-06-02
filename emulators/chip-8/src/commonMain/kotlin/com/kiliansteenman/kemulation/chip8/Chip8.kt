@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulation.chip8

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Chip8(
    private val input: Input,
) {
    private val display = Display(64, 32)
    private val audio = Audio()
    private var runJob: Job? = null

    private var _isRunning: Boolean = false
    val isRunning: Boolean
        get() = _isRunning

    private val cpu = Cpu(
        state = CpuState(),
        display = display,
        inputState = input.state
    )

    private val _pixels = MutableStateFlow(display.pixels.toList())
    val pixels: Flow<List<Boolean>> = _pixels

    fun loadRom(rom: UByteArray) {
        cpu.loadProgram(rom)
    }

    fun start() {
        _isRunning = true

        runJob = GlobalScope.launch {
            while (true) {
                delay(((1f / 60) * 100).toLong())
                cpu.executeProgram()

                _pixels.emit(display.pixels.toList())

                // TODO: Play audio when audio flag is set
            }
        }
    }

    fun pause() {
        runJob?.cancel()
    }

    fun reset() {

    }
}