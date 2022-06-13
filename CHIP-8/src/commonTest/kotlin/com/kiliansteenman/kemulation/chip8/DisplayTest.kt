package com.kiliansteenman.kemulation.chip8

import kotlin.test.Test
import kotlin.test.assertFalse

internal class DisplayTest {

    @Test
    fun whenScreenIsCleared_thenPixelsAreAllCleared() {
        val display = Display(2, 2).apply {
            pixels[0] = true
        }

        display.clear()

        assertFalse { display.pixels[0] }
    }
}