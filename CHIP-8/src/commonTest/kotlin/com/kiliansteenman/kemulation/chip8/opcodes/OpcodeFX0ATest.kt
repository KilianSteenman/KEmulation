package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX0ATest : OpcodeTest() {

    @Test
    fun whenWaitForKeyPressIsCalled_andNoKeyIsPressed_thenProgramCounterIsDecremented() {
        state.setProgramCounter(0x2)
        cpu.executeOpcode(0xF10A.toShort())

        assertEquals(0x0.toUByte(), state.registers[0x1], "Register is not set")
        assertEquals(0x0, state.programCounter)
    }

    @Test
    fun whenWaitForKeyPressIsCalled_andKeyIsPressed_thenKeyIsStoredInRegister() {
        input.setKeyPressed(0xE, true)

        cpu.executeOpcode(0xF10A.toShort())

        assertEquals(0xE.toUByte(), state.registers[0x1])
    }
}