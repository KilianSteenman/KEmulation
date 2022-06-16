package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XX3Test : OpcodeTest() {

    @Test
    fun whenStoreXORValueInRegister_thenXORValueIsStored() {
        state.registers[1] = 0xFE.toUByte()
        state.registers[2] = 0x0E.toUByte()

        cpu.executeOpcode(0x8123.toShort())

        assertEquals(0xF0.toUByte(), state.registers[1])
    }
}