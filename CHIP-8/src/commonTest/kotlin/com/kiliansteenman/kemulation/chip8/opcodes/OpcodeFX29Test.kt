package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX29Test : OpcodeTest() {

    @Test
    fun whenPointIndexToCharacterIsCalled_thenIndexIsSetToPointToCharacter() {
        state.registers[1] = 0x0.toUByte() // 0
        cpu.executeOpcode(0xF129.toShort())
        assertEquals(0x050.toShort(), state.index)

        state.registers[1] = 0x1.toUByte() // 1
        cpu.executeOpcode(0xF129.toShort())
        assertEquals(0x055.toShort(), state.index)
    }
}