package com.kiliansteenman.kemulation.chip8

class Display(
    val width: Int,
    val height: Int
) {

    val pixels = BooleanArray(width * height) { false }

    fun clear() {
        pixels.forEachIndexed { index, _ -> pixels[index] = false }
    }
}