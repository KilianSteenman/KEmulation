package com.kiliansteenman.kemulation.chip8.opcodes

import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.CpuState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class Opcode00EETest : OpcodeTest() {

    @Test
    fun whenReturnFromSubroutineIsCalled_thenProgramCounterIsRestored() {
        val state = CpuState()
        val cpu = Cpu(state, display, inputState)

        state.setProgramCounter(0x0200.toShort())

        cpu.executeOpcode(0x2242.toShort())
        cpu.executeOpcode(0x00EE.toShort())

        assertEquals(0x0200.toShort(), state.programCounter)
        assertTrue(state.stack.isEmpty())
    }
}