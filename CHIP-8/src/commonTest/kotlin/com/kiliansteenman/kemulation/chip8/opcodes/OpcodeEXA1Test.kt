package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeEXA1Test : OpcodeTest() {

    @Test
    fun whenIsKeyNotPressedIsCalled_andKeyIsPressed_thenNextInstructionIsNotSkipped() {
        inputState.setKeyPressed(keyIndex = 0xA, isPressed = true)
        state.registers[0x1] = 0xA.toUByte()

        cpu.executeOpcode(0xE1A1.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }

    @Test
    fun whenIsKeyNotPressedIsCalled_andKeyIsNotPressed_thenNextInstructionIsSkipped() {
        inputState.setKeyPressed(keyIndex = 0xA, isPressed = false)
        state.registers[0x1] = 0xA.toUByte()

        cpu.executeOpcode(0xE1A1.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }
}