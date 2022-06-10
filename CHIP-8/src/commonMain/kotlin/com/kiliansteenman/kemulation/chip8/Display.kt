package com.kiliansteenman.kemulation.chip8

import kotlin.experimental.and

class Display(
    private val width: Int,
    private val height: Int
) {

    val pixels = BooleanArray(width * height) { false }

    fun clear() {
        pixels.forEachIndexed { index, _ -> pixels[index] = false }
    }

    fun drawSprite(xOffset: UByte, yOffset: UByte, sprite: UByteArray) {
        var x = xOffset.toInt() % width
        var y = yOffset.toInt() % height

        println("Drawing at $x ($xOffset) $y ($yOffset) : ${sprite.map { it.toString(2) }}")

        sprite.forEach { spriteByte ->
            writePixel(x, y, spriteByte.and(0x80.toUByte()) == 0x80.toUByte())
            x++
            writePixel(x, y, spriteByte.and(0x40.toUByte()) == 0x40.toUByte())
            x++
            writePixel(x, y, spriteByte.and(0x20.toUByte()) == 0x20.toUByte())
            x++
            writePixel(x, y, spriteByte.and(0x10.toUByte()) == 0x10.toUByte())
            x++

            writePixel(x, y, spriteByte.and(0x08.toUByte()) == 0x08.toUByte())
            x++
            writePixel(x, y, spriteByte.and(0x04.toUByte()) == 0x04.toUByte())
            x++
            writePixel(x, y, spriteByte.and(0x02.toUByte()) == 0x02.toUByte())
            x++
            writePixel(x, y, spriteByte.and(0x01.toUByte()) == 0x01.toUByte())
            x++

            y++
            x = xOffset.toInt() % width
        }
    }

    private fun writePixel(x: Int, y: Int, enabled: Boolean) {
        if (x >= width || y >= height) return

        val pixel = (y * width) + x
        pixels[pixel] = pixels[pixel].xor(enabled)
    }
}