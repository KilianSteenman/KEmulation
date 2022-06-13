package com.kiliansteenman.kemulation.chip8

import kotlin.test.Test
import kotlin.test.assertEquals

internal class CpuTest {

    private val display = Display(1, 1)
    private val input = Input()

    @Test
    fun whenCpuIsCreated_thenFontIsCopiedInMemory() {
        val state = CpuState()
        Cpu(state, display, input)

        // Checking the first and last part is fine for now
        assertEquals(0xF0.toUByte(), state.memory[0x050])
        assertEquals(0x80.toUByte(), state.memory[0x09F])
    }
}
