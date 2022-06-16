package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Opcode2XXXTest : OpcodeTest() {

    @Test
    fun whenJumpToSubroutineIsCalled_thenProgramCounterIsUpdated_andAddressIsStored() {
        state.setProgramCounter(0x0200.toShort())

        cpu.executeOpcode(0x2242.toShort())
        assertEquals(0x0242.toShort(), state.programCounter)
        assertEquals(0x0200.toShort(), state.stack.first())

        cpu.executeOpcode(0x2250.toShort())
        assertEquals(0x0250.toShort(), state.programCounter)
        assertEquals(0x0242.toShort(), state.stack.first())
    }
}