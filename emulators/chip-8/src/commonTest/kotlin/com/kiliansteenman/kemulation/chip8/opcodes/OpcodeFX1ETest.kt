package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX1ETest : OpcodeTest() {

    @Test
    fun whenAddRegisterToIndexIsCalled_thenValueIsAddedToIndex() {
        state.index = 0x1
        state.registers[0x1] = 0x02.toUByte()

        cpu.executeOpcode(0xF11E.toShort())

        assertEquals(0x03, state.index)
    }
}