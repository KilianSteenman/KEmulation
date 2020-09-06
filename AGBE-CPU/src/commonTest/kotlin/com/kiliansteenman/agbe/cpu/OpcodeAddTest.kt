package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeAddTest : OpcodeBaseTest() {

    private val values = arrayOf(
        MathOperation(0x87.toByte(), 'A', 'A', 0x1, 0x1, 0x2),
        MathOperation(0x80.toByte(), 'A', 'B', 0x1, 0x2, 0x3),
        MathOperation(0x81.toByte(), 'A', 'C', 0x1, 0x2, 0x3),
        MathOperation(0x82.toByte(), 'A', 'D', 0x1, 0x2, 0x3),
        MathOperation(0x83.toByte(), 'A', 'E', 0x1, 0x2, 0x3),
        MathOperation(0x84.toByte(), 'A', 'H', 0x1, 0x2, 0x3),
        MathOperation(0x85.toByte(), 'A', 'L', 0x1, 0x2, 0x3),
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

    @Test
    fun when_add_result_equals_zero_z_flag_is_set() {
        val addProgram = byteArrayOf(0x87.toByte())

        performProgram(addProgram)

        assertTrue(registers.isZeroFlagSet())
    }
}