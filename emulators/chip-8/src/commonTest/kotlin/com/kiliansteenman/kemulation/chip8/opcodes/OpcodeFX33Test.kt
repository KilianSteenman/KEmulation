package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX33Test : OpcodeTest() {

    @Test
    fun whenStoreBinaryCodedDecimalIsCalled_thenValueIsStored() {
        state.index = 0x0250
        state.registers[1] = 0xFF.toUByte()
        cpu.executeOpcode(0xF133.toShort())

        assertEquals(2.toUByte(), state.memory[0x0250])
        assertEquals(5.toUByte(), state.memory[0x0251])
        assertEquals(5.toUByte(), state.memory[0x0252])
    }
}