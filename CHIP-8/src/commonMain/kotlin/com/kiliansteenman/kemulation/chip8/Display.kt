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

    fun drawSprite(xOffset: Byte, yOffset: Byte, sprite: ByteArray) {
        var x = xOffset.toInt() % width
        var y = yOffset.toInt() % height

        println("Drawing at $x ($xOffset) $y ($yOffset) : ${sprite.map { it.toString(2) }}")

        sprite.forEach { spriteByte ->
            writePixel(x, y, spriteByte.and(0x80.toByte()) == 0x80.toByte())
            x++
            writePixel(x, y, spriteByte.and(0x40.toByte()) == 0x40.toByte())
            x++
            writePixel(x, y, spriteByte.and(0x20.toByte()) == 0x20.toByte())
            x++
            writePixel(x, y, spriteByte.and(0x10.toByte()) == 0x10.toByte())
            x++

            writePixel(x, y, spriteByte.and(0x08.toByte()) == 0x08.toByte())
            x++
            writePixel(x, y, spriteByte.and(0x04.toByte()) == 0x04.toByte())
            x++
            writePixel(x, y, spriteByte.and(0x02.toByte()) == 0x02.toByte())
            x++
            writePixel(x, y, spriteByte.and(0x01.toByte()) == 0x01.toByte())
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