package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX07Test : OpcodeTest() {

    @Test
    fun whenStoreDelayTimerIsCalled_thenDelayTimerIsStoredInRegister() {
        state.delayTimer = 0x55.toUByte()
        cpu.executeOpcode(0xF107.toShort())

        assertEquals(0x55.toUByte(), state.registers[1])
    }
}