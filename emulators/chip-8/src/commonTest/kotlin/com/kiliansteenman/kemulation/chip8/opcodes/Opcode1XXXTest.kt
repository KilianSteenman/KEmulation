package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode1XXXTest : OpcodeTest() {

    @Test
    fun programCounterIsSetToAddress() {
        cpu.executeOpcode(0x1123)

        assertEquals(0x0123, state.programCounter)
    }
}