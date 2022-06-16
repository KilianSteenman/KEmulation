package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalStdlibApi
class OpcodePushTest: OpcodeBaseTest() {

    internal data class PushOpcode(val opcode: Byte, val r1: Char, val r2: Char)

    private val pushValues = arrayOf(
        PushOpcode(0xF5.toByte(), 'A','F'),
        PushOpcode(0xC5.toByte(), 'B','C'),
        PushOpcode(0xD5.toByte(), 'D','E'),
        PushOpcode(0xE5.toByte(), 'H','L')
    )

    @Test
    fun opcode_PUSH_nn() {
        pushValues.forEach { pushValue ->
            registers.reset()

            registers.setValue(pushValue.r1, 0x01)
            registers.setValue(pushValue.r2, 0x02)

            performOpcode(byteArrayOf(pushValue.opcode))

            var stackValue = memoryMap.readByte(registers.sp)
            assertEquals(0x02, stackValue)

            stackValue = memoryMap.readByte(registers.sp + 1)
            assertEquals(0x01, stackValue)
        }
    }
}