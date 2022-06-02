package com.kiliansteenman.kemulation.chip8

import kotlin.experimental.and

class Display(
    private val width: Int,
    private val height: Int
) {

    val pixels = BooleanArray(width * height)

    fun clear() {
        pixels.forEachIndexed { index, _ -> pixels[index] = false }
    }

    fun drawSprite(x: Byte, y: Byte, sprite: ByteArray) {
        var pixelIndex = (x * width) + (y * height)
        sprite.forEach { spriteByte ->
            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x80.toByte()) == 0x80.toByte())
            pixelIndex++
            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x40.toByte()) == 0x40.toByte())
            pixelIndex++
            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x20.toByte()) == 0x20.toByte())
            pixelIndex++
            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x10.toByte()) == 0x10.toByte())
            pixelIndex++

            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x08.toByte()) == 0x08.toByte())
            pixelIndex++
            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x04.toByte()) == 0x04.toByte())
            pixelIndex++
            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x02.toByte()) == 0x02.toByte())
            pixelIndex++
            pixels[pixelIndex] = pixels[pixelIndex].xor(spriteByte.and(0x01.toByte()) == 0x01.toByte())
            pixelIndex++

            pixelIndex += 56
        }
    }
}