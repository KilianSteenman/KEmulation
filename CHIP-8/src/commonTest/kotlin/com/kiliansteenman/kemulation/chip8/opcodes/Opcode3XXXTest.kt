package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode3XXXTest : OpcodeTest() {

    @Test
    fun whenSkipWhenTrueIsCalled_andValueIsEqual_thenNextInstructionIsSkipped() {
        state.registers[2] = 0x37.toUByte()
        cpu.executeOpcode(0x3237.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenTrueIsCalled_andValueIsNotEqual_thenNextInstructionIsNotSkipped() {
        state.registers[2] = 0x36.toUByte()
        cpu.executeOpcode(0x3237.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }
}