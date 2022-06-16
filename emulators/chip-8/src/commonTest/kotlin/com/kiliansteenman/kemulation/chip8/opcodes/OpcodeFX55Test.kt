package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX55Test : OpcodeTest() {

    @Test
    fun whenStoreRegistersIsCalled_thenRegistersAreStoredInMemoryAtIndex() {
        state.index = 0x0250
        state.registers[0] = 0.toUByte()
        state.registers[1] = 1.toUByte()
        state.registers[2] = 2.toUByte()
        state.registers[3] = 3.toUByte()

        cpu.executeOpcode(0xF455.toShort())

        assertEquals(0.toUByte(), state.memory[0x0250])
        assertEquals(1.toUByte(), state.memory[0x0251])
        assertEquals(2.toUByte(), state.memory[0x0252])
        assertEquals(3.toUByte(), state.memory[0x0253])
        assertEquals(0.toUByte(), state.memory[0x0254])
    }
}