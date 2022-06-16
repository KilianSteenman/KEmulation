package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeEX9ETest : OpcodeTest() {

    @Test
    fun whenIsKeyPressedIsCalled_andKeyIsPressed_thenNextInstructionIsSkipped() {
        inputState.setKeyPressed(keyIndex = 0xA, isPressed = true)
        state.registers[0x1] = 0xA.toUByte()

        cpu.executeOpcode(0xE19E.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenIsKeyPressedIsCalled_andKeyIsNotPressed_thenNextInstructionIsNotSkipped() {
        inputState.setKeyPressed(keyIndex = 0xA, isPressed = false)
        state.registers[0x1] = 0xA.toUByte()

        cpu.executeOpcode(0xE19E.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }
}