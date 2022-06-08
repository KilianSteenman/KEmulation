package com.kiliansteenman.kemulation.chip8

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DisplayTest {

    @Test
    fun whenScreenIsCleared_thenPixelsAreAllCleared() {
        val display = Display(2, 2).apply {
            pixels[0][0] = true
        }

        display.clear()

        assertFalse { display.pixels[0][0] }
    }

    @Test
    fun whenDrawSpriteIsCalled_thenSpriteIsDrawn() {
        val display = Display(64, 32)

        display.drawSprite(0, 0, byteArrayOf(0x90.toByte()))

        assertTrue { display.pixels[0][0] }
        assertFalse { display.pixels[0][1] }
        assertFalse { display.pixels[0][2] }
        assertTrue { display.pixels[0][3] }
        assertFalse { display.pixels[0][4] }
        assertFalse { display.pixels[0][5] }
        assertFalse { display.pixels[0][6] }
        assertFalse { display.pixels[0][7] }
    }

    @Test
    fun whenDrawSpriteIsCalledOutsideTheBoundsOfTheScreen_thenSpriteIsDrawn() {
        val display = Display(64, 32)

        display.drawSprite(68, 34, byteArrayOf(0x90.toByte()))

        assertTrue { display.pixels[4][2] }
        assertFalse { display.pixels[4][3] }
        assertFalse { display.pixels[4][4] }
        assertTrue { display.pixels[4][5] }
        assertFalse { display.pixels[4][6] }
        assertFalse { display.pixels[4][7] }
        assertFalse { display.pixels[4][8] }
        assertFalse { display.pixels[4][9] }
    }

    @Test
    fun whenPartOfSpriteIsDrawnOutsideTheBounds_thenSpriteIsNotDrawnOutsideTheBounds() {
        val display = Display(64, 32)

        display.drawSprite(60, 0, byteArrayOf(0x90.toByte()))
        assertTrue { display.pixels[60][0] }
        assertFalse { display.pixels[61][0] }
        assertFalse { display.pixels[62][0] }
        assertTrue { display.pixels[63][0] }
    }
}