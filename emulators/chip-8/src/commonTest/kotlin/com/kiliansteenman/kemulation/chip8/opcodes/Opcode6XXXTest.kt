package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode6XXXTest : OpcodeTest() {

    @Test
    fun constantShouldBeMovedToRegister() {
        cpu.executeOpcode(0x6227)

        assertEquals(0x27.toUByte(), state.registers[2])
    }
}