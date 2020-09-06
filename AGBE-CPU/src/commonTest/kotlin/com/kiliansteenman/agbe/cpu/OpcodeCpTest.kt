package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeCpTest : OpcodeBaseTest() {

    private val values = arrayOf(
        MathOperation(0xBF.toByte(), 'A', 'A', 0b11110000, 0b11110000, 0b00000000),
        MathOperation(0xB8.toByte(), 'A', 'B', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xB9.toByte(), 'A', 'C', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xBA.toByte(), 'A', 'D', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xBB.toByte(), 'A', 'E', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xBC.toByte(), 'A', 'H', 0b01110010, 0b11010001, 0b10100011),
        MathOperation(0xBD.toByte(), 'A', 'L', 0b01110010, 0b11010001, 0b10100011),
    )

    @Test
    fun when_values_are_equal_then_zero_flag_is_set() {
        values.forEach { value ->
            registers.reset()

            registers.setValue(value.r1, 1)
            registers.setValue(value.r2, 1)

            performProgram(byteArrayOf(value.opcode))

            assertTrue(registers.isZeroFlagSet(), "Expected zero flag to be set")
        }
    }

    @Test
    fun when_values_are_NOT_equal_then_zero_flag_is_NOT_set() {
        // Skip the first one, that values is always equal as it compares itself
        values.toList().subList(1, values.size).forEach { value ->
            registers.reset()

            registers.setValue(value.r1, 1)
            registers.setValue(value.r2, 2)

            performProgram(byteArrayOf(value.opcode))

            assertFalse(registers.isZeroFlagSet(), "Expected zero flag to be reset")
        }
    }

    private val opcodes = byteArrayOf(
        0xBF.toByte(),
        0xB8.toByte(),
        0xB9.toByte(),
        0xBA.toByte(),
        0xBB.toByte(),
        0xBC.toByte(),
        0xBD.toByte()
    )

    @Test
    fun when_CP_opcode_is_executed_flags_are_set() {
        opcodes.forEach { opcode ->
            performProgram(byteArrayOf(opcode))

            assertTrue(registers.isSubtractFlagSet(), "Subtract flag should be reset")
            assertFalse(registers.isHalfCarryFlagSet(), "HalfCarry flag should be reset")
            assertFalse(registers.isCarryFlagSet(), "Carry flag should be reset")
        }
    }
}