package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX15Test : OpcodeTest() {

    @Test
    fun whenSetDelayTimerIsCalled_thenDelayTimerIsSet() {
        state.registers[2] = 37.toUByte()

        cpu.executeOpcode(0xF215.toShort())

        assertEquals(37.toUByte(), state.delayTimer)
    }
}