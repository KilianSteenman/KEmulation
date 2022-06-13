package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode5XXXTest : OpcodeTest() {

    @Test
    fun whenSkipWhenRegistersAreEqualIsCalled_andValueIsEqual_thenNextInstructionIsSkipped() {
        state.registers[2] = 0x37.toUByte()
        state.registers[3] = 0x37.toUByte()
        cpu.executeOpcode(0x5230.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenRegistersAreEqualIsCalled_andValueIsNotEqual_thenNextInstructionIsNotSkipped() {
        state.registers[2] = 0x37.toUByte()
        state.registers[3] = 0x36.toUByte()
        cpu.executeOpcode(0x5230.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }
}