package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeSubTest : OpcodeBaseTest() {

    private val values = arrayOf(
        MathOperation(0x97.toByte(), 'A', 'A', 0x1, 0x1, 0x0),
        MathOperation(0x90.toByte(), 'A', 'B', 0x2, 0x1, 0x1),
        MathOperation(0x91.toByte(), 'A', 'C', 0x2, 0x1, 0x1),
        MathOperation(0x92.toByte(), 'A', 'D', 0x2, 0x1, 0x1),
        MathOperation(0x93.toByte(), 'A', 'E', 0x2, 0x1, 0x1),
        MathOperation(0x94.toByte(), 'A', 'H', 0x2, 0x1, 0x1),
        MathOperation(0x95.toByte(), 'A', 'L', 0x2, 0x1, 0x1),
    )

    @Test
    fun opcode_SUB_nn() {
        values.forEach { value ->
            registers.reset()
            registers.setValue(value.r1, value.r1Value)
            registers.setValue(value.r2, value.r2Value)

            performOpcode(byteArrayOf(value.opcode))

            registers.assertRegister(value.outputValue, value.r1)
        }
    }

    @Test
    fun when_sub_result_equals_zero_z_flag_is_set() {
        registers.a = 0x1

        val addProgram = byteArrayOf(0x97.toByte())

        performOpcode(addProgram)

        assertTrue(registers.isZeroFlagSet())
    }
}