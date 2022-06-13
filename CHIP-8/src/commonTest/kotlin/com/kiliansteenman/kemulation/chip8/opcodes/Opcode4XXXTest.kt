package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode4XXXTest : OpcodeTest() {

    @Test
    fun whenSkipWhenFalseIsCalled_andValueIsEqual_thenNextInstructionIsNotSkipped() {
        state.registers[2] = 0x37.toUByte()
        cpu.executeOpcode(0x4237.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenFalseIsCalled_andValueIsNotEqual_thenNextInstructionIsSkipped() {
        state.registers[2] = 0x36.toUByte()
        cpu.executeOpcode(0x4237.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }
}