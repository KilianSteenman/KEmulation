package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XX6Test : OpcodeTest() {

    @Test
    fun whenShiftRegisterRight_thenRegisterIsShiftedRight() {
        state.registers[1] = 0x02.toUByte()

        cpu.executeOpcode(0x8126.toShort())

        assertEquals(0x01.toUByte(), state.registers[1])
        assertEquals(0x00.toUByte(), state.registers[0xF])
    }
}