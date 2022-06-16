package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XX1Test : OpcodeTest() {

    @Test
    fun whenStoreOrValueInRegister_thenOrValueIsStored() {
        state.registers[1] = 0xFE.toUByte()
        state.registers[2] = 0x0E.toUByte()

        cpu.executeOpcode(0x8121.toShort())

        assertEquals(0xFE.toUByte(), state.registers[1])
    }
}