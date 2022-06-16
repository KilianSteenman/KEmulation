package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode7XXXTest : OpcodeTest() {

    @Test
    fun whenAddConstantToRegisterIsCalled_thenConstantShouldBeAddedToRegister() {
        // First add 1
        cpu.executeOpcode(0x7201)
        assertEquals(0x01.toUByte(), state.registers[2])

        // Now add another 2
        cpu.executeOpcode(0x7202)
        assertEquals(0x03.toUByte(), state.registers[2])
    }
}