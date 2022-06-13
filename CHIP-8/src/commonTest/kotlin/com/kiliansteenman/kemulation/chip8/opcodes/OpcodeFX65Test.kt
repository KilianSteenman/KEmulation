package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals

internal class OpcodeFX65Test : OpcodeTest() {

    @Test
    fun whenLoadRegistersIsCalled_thenMemoryIsStoredInRegisters() {
        state.index = 0x0250
        state.memory[0x0250] = 0.toUByte()
        state.memory[0x0251] = 1.toUByte()
        state.memory[0x0252] = 2.toUByte()
        state.memory[0x0253] = 3.toUByte()
        state.memory[0x0254] = 4.toUByte()

        cpu.executeOpcode(0xF465.toShort())

        assertEquals(0.toUByte(), state.registers[0])
        assertEquals(1.toUByte(), state.registers[1])
        assertEquals(2.toUByte(), state.registers[2])
        assertEquals(3.toUByte(), state.registers[3])
        assertEquals(4.toUByte(), state.registers[4])
        assertEquals(0.toUByte(), state.registers[5])
    }
}