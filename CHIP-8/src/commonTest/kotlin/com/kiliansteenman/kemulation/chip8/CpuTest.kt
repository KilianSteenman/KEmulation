package com.kiliansteenman.kemulation.chip8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class CpuTest {

    private val display = Display(1, 1)

    @Test
    fun whenCpuIsCreated_thenFontIsCopiedInMemory() {
        val state = CpuState()
        Cpu(state, display)

        // Checking the first and last part is fine for now
        assertEquals(0xF0.toByte(), state.memory[0x050])
        assertEquals(0x80.toByte(), state.memory[0x09F])
    }

    @Test
    fun whenClearScreenOpcodeIsCalled_thenScreenIsCleared() {
        val cpu = Cpu(display = display)

        display.pixels[0] = true

        cpu.executeOpcode(0x00E0)

        assertTrue { display.pixels.all { !it } }
    }

    @Test
    fun whenJumpOpcodeIsCalled_thenProgramCounterIsSetToAddress() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        cpu.executeOpcode(0x1123)

        assertEquals(0x0123, state.programCounter)
    }

    @Test
    fun whenMoveConstantToRegisterIsCalled_thenConstantShouldBeInRegister() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        cpu.executeOpcode(0x6227)

        assertEquals(0x27.toByte(), state.registers[2])
    }

    @Test
    fun whenAddConstantToRegisterIsCalled_thenConstantShouldBeAddedToRegister() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        // First add 1
        cpu.executeOpcode(0x7201)
        assertEquals(0x01.toByte(), state.registers[2])

        // Now add another 2
        cpu.executeOpcode(0x7202)
        assertEquals(0x03.toByte(), state.registers[2])
    }

    @Test
    fun whenSetIndexRegisterIsCalled_thenIndexRegisterIsSet() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        cpu.executeOpcode(0xA123.toShort())
        assertEquals(0x0123, state.index)
    }

    @Test
    fun whenDisplayOpcodeIsCalled_thenSpriteIsWrittenToTheDisplay() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        cpu.executeOpcode(0xD123.toShort())

        // TODO: Implement test
        assertTrue { false }
    }
}