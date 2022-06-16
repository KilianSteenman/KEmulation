package com.kiliansteenman.kemulation.chip8.opcodes

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class OpcodeDXXXTest : OpcodeTest() {

    @Test
    fun whenDrawSpriteIsCalled_thenSpriteIsDrawn() {
        state.memory[0x0250] = 0x90.toUByte()
        state.index = 0x0250
        state.registers[0] = 0.toUByte()
        state.registers[1] = 0.toUByte()

        cpu.executeOpcode(0xD011.toShort())

        assertTrue { display.pixels[0] }
        assertFalse { display.pixels[1] }
        assertFalse { display.pixels[2] }
        assertTrue { display.pixels[3] }
        assertFalse { display.pixels[4] }
        assertFalse { display.pixels[5] }
        assertFalse { display.pixels[6] }
        assertFalse { display.pixels[7] }
    }

    @Test
    fun whenDrawSpriteIsCalledOutsideTheBoundsOfTheScreen_thenSpriteIsDrawn() {
        state.memory[0x0250] = 0x90.toUByte()
        state.index = 0x0250
        state.registers[0] = 68.toUByte()
        state.registers[1] = 34.toUByte()

        cpu.executeOpcode(0xD011.toShort())

        assertTrue { display.pixels[132] }
        assertFalse { display.pixels[133] }
        assertFalse { display.pixels[134] }
        assertTrue { display.pixels[135] }
        assertFalse { display.pixels[136] }
        assertFalse { display.pixels[137] }
        assertFalse { display.pixels[138] }
        assertFalse { display.pixels[139] }
    }

    @Test
    fun whenPartOfSpriteIsDrawnOutsideTheBounds_thenSpriteIsNotDrawnOutsideTheBounds() {
        state.memory[0x0250] = 0x90.toUByte()
        state.index = 0x0250
        state.registers[0] = 60.toUByte()
        state.registers[1] = 0.toUByte()

        cpu.executeOpcode(0xD011.toShort())

        assertTrue { display.pixels[60] }
        assertFalse { display.pixels[61] }
        assertFalse { display.pixels[62] }
        assertTrue { display.pixels[63] }
    }

    @Test
    fun whenAnEnabledPixelIsTurnedOff_thenTrueIsReturned() {
        state.memory[0x0250] = 0xF0.toUByte()
        state.index = 0x0250
        state.registers[0] = 0.toUByte()
        state.registers[1] = 0.toUByte()

        cpu.executeOpcode(0xD011.toShort())
        assertTrue(display.pixels[0])
        assertEquals(0.toUByte(), state.registers[0xF])

        cpu.executeOpcode(0xD011.toShort())
        assertFalse(display.pixels[0])
        assertEquals(1.toUByte(), state.registers[0xF])
    }
}