package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode8XX0Test : OpcodeTest() {

    @Test
    fun whenMoveRegisterIsCalled_thenValueOfRegisterIsMoved() {
        state.registers[2] = 0x37.toUByte()
        state.registers[3] = 0x12.toUByte()
        cpu.executeOpcode(0x8230.toShort())

        assertEquals(0x12.toUByte(), state.registers[2])
    }
}