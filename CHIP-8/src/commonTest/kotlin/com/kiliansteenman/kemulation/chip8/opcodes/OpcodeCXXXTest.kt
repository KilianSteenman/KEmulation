package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeCXXXTest : OpcodeTest() {

    @Test
    fun whenGenerateRandomNumberIsCalled_thenRandomNumberIsStored() {
        random.providedNextInt = 0x7

        cpu.executeOpcode(0xC112.toShort())

        assertEquals(0x12, random.passedMaxValue)
        assertEquals(0x7.toUByte(), state.registers[1])
    }
}