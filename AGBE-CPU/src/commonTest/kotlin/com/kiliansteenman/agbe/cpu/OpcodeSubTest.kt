package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeSubTest : OpcodeBaseTest() {

    internal data class SubtractOpcode(
        val opcode: Byte,
        val r1: Char,
        val r2: Char,
        val r1Value: Int,
        val r2Value: Int,
        val outputValue: Int
    )

    private val values = arrayOf(
        SubtractOpcode(0x97.toByte(), 'A', 'A', 0x1, 0x1, 0x0),
        SubtractOpcode(0x90.toByte(), 'A', 'B', 0x2, 0x1, 0x1),
        SubtractOpcode(0x91.toByte(), 'A', 'C', 0x2, 0x1, 0x1),
        SubtractOpcode(0x92.toByte(), 'A', 'D', 0x2, 0x1, 0x1),
        SubtractOpcode(0x93.toByte(), 'A', 'E', 0x2, 0x1, 0x1),
        SubtractOpcode(0x94.toByte(), 'A', 'H', 0x2, 0x1, 0x1),
        SubtractOpcode(0x95.toByte(), 'A', 'L', 0x2, 0x1, 0x1),
    )

    @Test
    fun opcode_SUB_nn() {
        values.forEach { value ->
            registers.reset()
            registers.setValue(value.r1, value.r1Value)
            registers.setValue(value.r2, value.r2Value)

            performProgram(byteArrayOf(value.opcode))

            registers.assertRegister(value.outputValue, value.r1)
        }
    }

    @Test
    fun when_sub_result_equals_zero_z_flag_is_set() {
        registers.a = 0x1

        val addProgram = byteArrayOf(0x97.toByte())

        performProgram(addProgram)

        assertTrue(registers.isZeroFlagSet())
    }
}