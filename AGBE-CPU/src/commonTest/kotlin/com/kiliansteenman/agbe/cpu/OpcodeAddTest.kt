package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeAddTest : OpcodeBaseTest() {

    internal data class AddOpcode(
        val opcode: Byte,
        val r1: Char,
        val r2: Char,
        val r1Value: Int,
        val r2Value: Int,
        val outputValue: Int
    )

    private val values = arrayOf(
        AddOpcode(0x87.toByte(), 'A', 'A', 0x1, 0x1, 0x2),
        AddOpcode(0x80.toByte(), 'A', 'B', 0x1, 0x2, 0x3),
        AddOpcode(0x81.toByte(), 'A', 'C', 0x1, 0x2, 0x3),
        AddOpcode(0x82.toByte(), 'A', 'D', 0x1, 0x2, 0x3),
        AddOpcode(0x83.toByte(), 'A', 'E', 0x1, 0x2, 0x3),
        AddOpcode(0x84.toByte(), 'A', 'H', 0x1, 0x2, 0x3),
        AddOpcode(0x85.toByte(), 'A', 'L', 0x1, 0x2, 0x3),
    )

    @Test
    fun opcode_ADD_nn() {
        values.forEach { value ->
            registers.reset()
            registers.setValue(value.r1, value.r1Value)
            registers.setValue(value.r2, value.r2Value)

            performProgram(byteArrayOf(value.opcode))

            registers.assertRegister(value.outputValue, value.r1)
        }
    }
}