package com.kiliansteenman.kemulation.chip8.opcodes

import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.CpuState
import com.kiliansteenman.kemulation.chip8.Display
import com.kiliansteenman.kemulation.chip8.InputState
import kotlin.random.Random

internal abstract class OpcodeTest {

    val state = CpuState()
    val display = Display(64, 32)
    val inputState = InputState()
    val random = TestRandom()
    val cpu = Cpu(state, display, inputState, random)
}

internal class TestRandom(
    var providedNextInt: Int = 0
) : Random() {

    var passedMaxValue: Int = 0

    override fun nextBits(bitCount: Int): Int {
        return 0
    }

    override fun nextInt(until: Int): Int {
        passedMaxValue = until
        return providedNextInt
    }
}