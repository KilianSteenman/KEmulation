package com.kiliansteenman.kemulation.chip8

class Display(
    private val width: Int,
    private val height: Int
) {

    val pixels = BooleanArray(width * height) { false }

    fun clear() {
        pixels.forEachIndexed { index, _ -> pixels[index] = false }
    }

    fun drawSprite(xOffset: UByte, yOffset: UByte, sprite: UByteArray): Boolean {
        var x = xOffset.toInt() % width
        var y = yOffset.toInt() % height

        println("Drawing at $x ($xOffset) $y ($yOffset) : ${sprite.map { it.toString(2) }}")

        var isPixelTurnedOff = false
        sprite.forEach { spriteByte ->
            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x80.toUByte()) == 0x80.toUByte()))
            x++
            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x40.toUByte()) == 0x40.toUByte()))
            x++
            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x20.toUByte()) == 0x20.toUByte()))
            x++
            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x10.toUByte()) == 0x10.toUByte()))
            x++

            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x08.toUByte()) == 0x08.toUByte()))
            x++
            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x04.toUByte()) == 0x04.toUByte()))
            x++
            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x02.toUByte()) == 0x02.toUByte()))
            x++
            isPixelTurnedOff = isPixelTurnedOff.or(writePixel(x, y, spriteByte.and(0x01.toUByte()) == 0x01.toUByte()))
            x++

            y++
            x = xOffset.toInt() % width
        }
        return isPixelTurnedOff.also {
            if(isPixelTurnedOff) {
                println("Returning turned off pixel")
            }
        }
    }

    private fun writePixel(x: Int, y: Int, enabled: Boolean): Boolean {
        if (x >= width || y >= height) return false

        val pixel = (y * width) + x

        val isPixelToggled = pixels[pixel].and(enabled)
        pixels[pixel] = pixels[pixel].xor(enabled)
        return isPixelToggled
    }
}