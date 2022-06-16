package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeAXXXTest : OpcodeTest() {

    @Test
    fun indexIsSet() {
        cpu.executeOpcode(0xA123.toShort())
        assertEquals(0x0123, state.index)
    }
}