package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX18Test : OpcodeTest() {

    @Test
    fun whenSetSoundTimerIsCalled_thenSoundTimerIsSet() {
        state.registers[0x1] = 0xFE.toUByte()

        cpu.executeOpcode(0xF118.toShort())

        assertEquals(0xFE.toUByte(), state.soundTimer)
    }
}