package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeXorTest : OpcodeBaseTest() {

    private val values = arrayOf(
        MathOperation(0xAF.toByte(), 'A', 'A', 0b11110000, 0b11110000, 0b00000000),
        MathOperation(0xA8.toByte(), 'A', 'B', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xA9.toByte(), 'A', 'C', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xAA.toByte(), 'A', 'D', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xAB.toByte(), 'A', 'E', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xAC.toByte(), 'A', 'H', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xAD.toByte(), 'A', 'L', 0b01110010, 0b11010001, 0b10100011),
    )

    @Test
    fun opcode_XOR_nn() {
        values.forEach { value ->
            registers.reset()
            registers.setValue(value.r1, value.r1Value)
            registers.setValue(value.r2, value.r2Value)

            performProgram(byteArrayOf(value.opcode))

            registers.assertRegister(value.outputValue, value.r1)
        }
    }

    private val xorOpcodes = byteArrayOf(
        0xAF.toByte(),
        0xA8.toByte(),
        0xA9.toByte(),
        0xAA.toByte(),
        0xAB.toByte(),
        0xAC.toByte(),
        0xAD.toByte()
    )

    @Test
    fun when_XOR_opcode_is_executed_flags_are_set() {
        xorOpcodes.forEach { opcode ->
            performProgram(byteArrayOf(opcode))

            assertFalse(registers.isSubtractFlagSet(), "Subtract flag should be reset")
            assertFalse(registers.isHalfCarryFlagSet(), "HalfCarry flag should be reset")
            assertFalse(registers.isCarryFlagSet(), "Carry flag should be reset")
        }
    }

    @Test
    fun when_XOR_result_equals_zero_z_flag_is_set() {
        registers.a = 0

        performProgram(byteArrayOf(0xAF.toByte()))

        assertTrue(registers.isZeroFlagSet())
    }
}