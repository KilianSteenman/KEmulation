package com.kiliansteenman.kemulation.chip8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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

        assertFalse { display.pixels[0] }
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
    fun whenSetDelayTimerIsCalled_thenDelayTimerIsSet() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 37
        cpu.executeOpcode(0xF215.toShort())

        assertEquals(37, state.delayTimer)
    }

    @Test
    fun whenSkipWhenTrueIsCalled_andValueIsEqual_thenNextInstructionIsSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x37
        cpu.executeOpcode(0x3237.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenTrueIsCalled_andValueIsNotEqual_thenNextInstructionIsNotSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x36
        cpu.executeOpcode(0x3237.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenFalseIsCalled_andValueIsEqual_thenNextInstructionIsNotSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x37
        cpu.executeOpcode(0x4237.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenFalseIsCalled_andValueIsNotEqual_thenNextInstructionIsSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x36
        cpu.executeOpcode(0x4237.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenRegistersAreEqualIsCalled_andValueIsEqual_thenNextInstructionIsSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x37
        state.registers[3] = 0x37
        cpu.executeOpcode(0x5230.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenRegistersAreEqualIsCalled_andValueIsNotEqual_thenNextInstructionIsNotSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x37
        state.registers[3] = 0x36
        cpu.executeOpcode(0x5230.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenRegistersAreNotEqualIsCalled_andValueIsNotEqual_thenNextInstructionIsSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x37
        state.registers[3] = 0x36
        cpu.executeOpcode(0x9230.toShort())

        assertEquals(2.toShort(), state.programCounter)
    }

    @Test
    fun whenSkipWhenRegistersAreNotEqualIsCalled_andValueIsEqual_thenNextInstructionIsNotSkipped() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x37
        state.registers[3] = 0x37
        cpu.executeOpcode(0x9230.toShort())

        assertEquals(0.toShort(), state.programCounter)
    }

    @Test
    fun whenMoveRegisterIsCalled_thenValueOfRegisterIsMoved() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[2] = 0x37
        state.registers[3] = 0x12
        cpu.executeOpcode(0x8230.toShort())

        assertEquals(0x12, state.registers[2])
    }

    @Test
    fun whenPointIndexToCharacterIsCalled_thenIndexIsSetToPointToCharacter() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.registers[1] = 0x0 // 0
        cpu.executeOpcode(0xF129.toShort())
        assertEquals(0x050.toShort(), state.index)

        state.registers[1] = 0x1 // 1
        cpu.executeOpcode(0xF129.toShort())
        assertEquals(0x055.toShort(), state.index)
    }

    @Test
    fun whenJumpToSubroutineIsCalled_thenProgramCounterIsUpdated_andAddressIsStored() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.setProgramCounter(0x0200.toShort())

        cpu.executeOpcode(0x2242.toShort())
        assertEquals(0x0242.toShort(), state.programCounter)
        assertEquals(0x0200.toShort(), state.stack.first())

        cpu.executeOpcode(0x2250.toShort())
        assertEquals(0x0250.toShort(), state.programCounter)
        assertEquals(0x0242.toShort(), state.stack.first())
    }

    @Test
    fun whenReturnFromSubroutineIsCalled_thenProgramCounterIsRestored() {
        val state = CpuState()
        val cpu = Cpu(state, display)

        state.setProgramCounter(0x0200.toShort())

        cpu.executeOpcode(0x2242.toShort())
        cpu.executeOpcode(0x00EE.toShort())

        assertEquals(0x0200.toShort(), state.programCounter)
        assertTrue(state.stack.isEmpty())
    }
}