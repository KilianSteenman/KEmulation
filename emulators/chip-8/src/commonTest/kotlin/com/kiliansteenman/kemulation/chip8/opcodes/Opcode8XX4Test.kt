package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XX4Test : OpcodeTest() {

    @Test
    fun whenStoreADDValueInRegister_thenADDValueIsStored() {
        state.registers[1] = 0x01.toUByte()
        state.registers[2] = 0x01.toUByte()

        cpu.executeOpcode(0x8124.toShort())

        assertEquals(0x02.toUByte(), state.registers[1])
        assertEquals(0x0.toUByte(), state.registers[0xF])
    }

    @Test
    fun whenStoreADDValueInRegisterAndValueOverflows_thenADDValueIsStoredAndFlagIsSet() {
        state.registers[1] = 0xFF.toUByte()
        state.registers[2] = 0x01.toUByte()

        cpu.executeOpcode(0x8124.toShort())

        assertEquals(0x00.toUByte(), state.registers[1])
        assertEquals(0x01.toUByte(), state.registers[0xF])
    }
}