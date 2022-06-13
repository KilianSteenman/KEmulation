package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XX2Test : OpcodeTest() {

    @Test
    fun whenStoreAndValueInRegister_thenAndValueIsStored() {
        state.registers[1] = 0xA2.toUByte()
        state.registers[2] = 0xFF.toUByte()

        cpu.executeOpcode(0x8122.toShort())

        assertEquals(0xA2.toUByte(), state.registers[1])
    }
}