package com.kiliansteenman.kemulation.chip8

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DisplayTest {

    @Test
    fun whenScreenIsCleared_thenPixelsAreAllCleared() {
        val display = Display(2, 2).apply {
            pixels[0] = true
        }

        display.clear()

        assertFalse { display.pixels[0] }
    }

    @Test
    fun whenDrawSpriteIsCalled_thenSpriteIsDrawn() {
        val display = Display(64, 32)

        display.drawSprite(0.toUByte(), 0.toUByte(), ubyteArrayOf(0x90.toUByte()))

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
        val display = Display(64, 32)

        display.drawSprite(68.toUByte(), 34.toUByte(), ubyteArrayOf(0x90.toUByte()))

//        assertTrue { display.pixels[4][2] }
//        assertFalse { display.pixels[4][3] }
//        assertFalse { display.pixels[4][4] }
//        assertTrue { display.pixels[4][5] }
//        assertFalse { display.pixels[4][6] }
//        assertFalse { display.pixels[4][7] }
//        assertFalse { display.pixels[4][8] }
//        assertFalse { display.pixels[4][9] }
    }

    @Test
    fun whenPartOfSpriteIsDrawnOutsideTheBounds_thenSpriteIsNotDrawnOutsideTheBounds() {
        val display = Display(64, 32)

        display.drawSprite(60.toUByte(), 0.toUByte(), ubyteArrayOf(0x90.toUByte()))
        assertTrue { display.pixels[60] }
        assertFalse { display.pixels[61] }
        assertFalse { display.pixels[62] }
        assertTrue { display.pixels[63] }
    }
}