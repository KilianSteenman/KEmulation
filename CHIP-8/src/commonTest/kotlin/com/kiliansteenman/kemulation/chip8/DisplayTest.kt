package com.kiliansteenman.kemulation.chip8

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DisplayTest {

    @Test
    fun whenScreenIsCleared_thenPixelsAreAllCleared() {
        val display = Display(2, 2).apply {
            pixels[1] = true
        }

        display.clear()
        assertTrue { display.pixels.all { !it } }
    }

    @Test
    fun whenDrawSpriteIsCalled_thenSpriteIsDrawn() {
        val display = Display(64, 32)

        display.drawSprite(0, 0, byteArrayOf(0x90.toByte()))

        assertTrue { display.pixels[0] }
        assertFalse { display.pixels[1] }
        assertFalse { display.pixels[2] }
        assertTrue { display.pixels[3] }
        assertFalse { display.pixels[4] }
        assertFalse { display.pixels[5] }
        assertFalse { display.pixels[6] }
        assertFalse { display.pixels[7] }
    }
}