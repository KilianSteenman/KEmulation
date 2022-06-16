package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XX7Test : OpcodeTest() {

    @Test
    fun whenStoreSubtractValueInRegisterAndValueOverflows_thenSubtractValueIsStored() {
        state.registers[1] = 0x01.toUByte()
        state.registers[2] = 0xFF.toUByte()

        cpu.executeOpcode(0x8127.toShort())

        assertEquals(0xFE.toUByte(), state.registers[1])
        assertEquals(0x01.toUByte(), state.registers[0xF])
    }

    @Test
    fun whenSubtractOverflows_thenOverflowRegisterIsSet() {
        state.registers[1] = 0x01.toUByte()
        state.registers[2] = 0x00.toUByte()

        cpu.executeOpcode(0x8127.toShort())

        assertEquals(0xFF.toUByte(), state.registers[1])
        assertEquals(0x00.toUByte(), state.registers[0xF])
    }
}