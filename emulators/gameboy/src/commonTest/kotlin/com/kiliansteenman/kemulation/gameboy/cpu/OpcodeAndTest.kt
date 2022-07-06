package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeAndTest : OpcodeBaseTest() {

    private val values = arrayOf(
        MathOperation(
            0xA7.toByte(),
            'A',
            'A',
            0b11110000,
            0b11110000,
            0b11110000
        ),
        MathOperation(
            0xA0.toByte(),
            'A',
            'B',
            0b11110010,
            0b11110001,
            0b11110000
        ),
        MathOperation(
            0xA1.toByte(),
            'A',
            'C',
            0b11110010,
            0b11110001,
            0b11110000
        ),
        MathOperation(
            0xA2.toByte(),
            'A',
            'D',
            0b11110010,
            0b11110001,
            0b11110000
        ),
        MathOperation(
            0xA3.toByte(),
            'A',
            'E',
            0b11110010,
            0b11110001,
            0b11110000
        ),
        MathOperation(
            0xA4.toByte(),
            'A',
            'H',
            0b11110010,
            0b11110001,
            0b11110000
        ),
        MathOperation(
            0xA5.toByte(),
            'A',
            'L',
            0b11110010,
            0b11110001,
            0b11110000
        ),
    )

    @Test
    fun opcode_AND_nn() {
        values.forEach { value ->
            registers.reset()
            registers.setValue(value.r1, value.r1Value)
            registers.setValue(value.r2, value.r2Value)

            performOpcode(byteArrayOf(value.opcode))

            registers.assertRegister(value.outputValue, value.r1)
        }
    }

    @Test
    fun when_and_result_equals_zero_z_flag_is_set() {
        registers.a = 0

        performOpcode(byteArrayOf(0xA7.toByte()))

        assertTrue(registers.isZeroFlagSet())
    }

    @Test
    fun when_and_is_performed_than_N_flag_is_reset() {
        registers.setSubtractFlag()

        performOpcode(byteArrayOf(0xA7.toByte()))

        assertFalse(registers.isSubtractFlagSet())
    }

    @Test
    fun when_and_is_performed_than_H_flag_is_set() {
        performOpcode(byteArrayOf(0xA7.toByte()))

        assertTrue(registers.isHalfCarryFlagSet())
    }

    @Test
    fun when_and_is_performed_than_C_flag_is_reset() {
        performOpcode(byteArrayOf(0xA7.toByte()))

        assertFalse(registers.isCarryFlagSet())
    }
}