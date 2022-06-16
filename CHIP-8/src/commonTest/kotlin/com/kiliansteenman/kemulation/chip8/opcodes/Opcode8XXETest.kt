package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XXETest : OpcodeTest() {

    @Test
    fun whenShiftRegisterLeft_thenRegisterIsShiftedLeft() {
        state.registers[1] = 0x01.toUByte()

        cpu.executeOpcode(0x812E.toShort())

        assertEquals(0x02.toUByte(), state.registers[1])
        assertEquals(0x00.toUByte(), state.registers[0xF])
    }

    @Test
    fun whenShiftRegisterLeft_thenShiftedOfBitIsStoredInRegisterF() {
        state.registers[1] = 0xFF.toUByte()

        cpu.executeOpcode(0x812E.toShort())

        assertEquals(0xFE.toUByte(), state.registers[1])
        assertEquals(0x01.toUByte(), state.registers[0xF])
    }
}