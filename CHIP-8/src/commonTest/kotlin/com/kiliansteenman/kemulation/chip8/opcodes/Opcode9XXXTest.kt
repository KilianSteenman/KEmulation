package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode9XXXTest : OpcodeTest() {

    @Test
    fun whenSkipWhenRegistersAreNotEqualIsCalled_andValueIsNotEqual_thenNextInstructionIsSkipped() {
        state.registers[2] = 0x37.toUByte()
        state.registers[3] = 0x36.toUByte()
        cpu.executeOpcode(0x9230.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenRegistersAreNotEqualIsCalled_andValueIsEqual_thenNextInstructionIsNotSkipped() {
        state.registers[2] = 0x37.toUByte()
        state.registers[3] = 0x37.toUByte()
        cpu.executeOpcode(0x9230.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }
}