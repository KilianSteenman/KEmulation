package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertFalse

internal class Opcode00E0Test : OpcodeTest() {

    @Test
    fun allPixelsOnTheScreenAreCleared() {
        display.pixels[0] = true

        cpu.executeOpcode(0x00E0)

        assertFalse { display.pixels[0] }
    }
}
