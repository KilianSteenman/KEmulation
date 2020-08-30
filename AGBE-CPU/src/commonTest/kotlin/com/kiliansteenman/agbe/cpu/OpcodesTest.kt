package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodesTest {

    private val registers = Registers()
    private val memoryMap = MemoryMap()
    private val cpu = Cpu(registers, memoryMap)

    internal data class LdOpcode(val opcode: Byte, val r1: Char, val r2: Char)

    private val ldR1R2Opcodes = arrayOf(
        LdOpcode(0x7F, 'A', 'A'),
        LdOpcode(0x78, 'A', 'B'),
        LdOpcode(0x79, 'A', 'C'),
        LdOpcode(0x7A, 'A', 'D'),
        LdOpcode(0x7B, 'A', 'E'),
        LdOpcode(0x7C, 'A', 'H'),
        LdOpcode(0x7D, 'A', 'L'),
        LdOpcode(0x40, 'B', 'B'),
        LdOpcode(0x41, 'B', 'C'),
        LdOpcode(0x42, 'B', 'D'),
        LdOpcode(0x43, 'B', 'E'),
        LdOpcode(0x44, 'B', 'H'),
        LdOpcode(0x45, 'B', 'L'),
        LdOpcode(0x48, 'C', 'B'),
        LdOpcode(0x49, 'C', 'C'),
        LdOpcode(0x4A, 'C', 'D'),
        LdOpcode(0x4B, 'C', 'E'),
        LdOpcode(0x4C, 'C', 'H'),
        LdOpcode(0x4D, 'C', 'L'),
        LdOpcode(0x50, 'D', 'B'),
        LdOpcode(0x51, 'D', 'C'),
        LdOpcode(0x52, 'D', 'D'),
        LdOpcode(0x53, 'D', 'E'),
        LdOpcode(0x54, 'D', 'H'),
        LdOpcode(0x55, 'D', 'L'),
        LdOpcode(0x58, 'E', 'B'),
        LdOpcode(0x59, 'E', 'C'),
        LdOpcode(0x5A, 'E', 'D'),
        LdOpcode(0x5B, 'E', 'E'),
        LdOpcode(0x5C, 'E', 'H'),
        LdOpcode(0x5D, 'E', 'L'),
        LdOpcode(0x60, 'H', 'B'),
        LdOpcode(0x61, 'H', 'C'),
        LdOpcode(0x62, 'H', 'D'),
        LdOpcode(0x63, 'H', 'E'),
        LdOpcode(0x64, 'H', 'H'),
        LdOpcode(0x65, 'H', 'L'),
        LdOpcode(0x68, 'L', 'B'),
        LdOpcode(0x69, 'L', 'C'),
        LdOpcode(0x6A, 'L', 'D'),
        LdOpcode(0x6B, 'L', 'E'),
        LdOpcode(0x6C, 'L', 'H'),
        LdOpcode(0x6D, 'L', 'L'),
    )

    internal data class Ld8BitValueOpcode(val opcode: Byte, val register: Char)

    private val ld8BitValue = arrayOf(
        Ld8BitValueOpcode(0x06, 'B'),
        Ld8BitValueOpcode(0x0E, 'C'),
        Ld8BitValueOpcode(0x16, 'D'),
        Ld8BitValueOpcode(0x1E, 'E'),
        Ld8BitValueOpcode(0x26, 'H'),
        Ld8BitValueOpcode(0x2E, 'L')
    )

    @Test
    fun opcode_LD_8_bit_value() {
        ld8BitValue.forEach {
            registers.reset()

            performProgram(byteArrayOf(it.opcode, 2))

            registers.assertRegister(2, it.register)
        }
    }

    internal data class Ld16BitValueOpcode(val opcode: Byte, val register: String)

    private val ld16BitValue = arrayOf(
        Ld16BitValueOpcode(0x01, "BC"),
        Ld16BitValueOpcode(0x11, "DE"),
        Ld16BitValueOpcode(0x21, "HL"),
        Ld16BitValueOpcode(0x31, "SP"),
    )

    @Test
    fun opcode_LD_16_bit_value() {
        ld16BitValue.forEach {
            registers.reset()

            performProgram(byteArrayOf(it.opcode, 0x0, 0x0.toByte()))

            registers.assertRegister(0, it.register)
        }
    }

    @Test
    fun opcode_LD_r1_r2() {
        ldR1R2Opcodes.forEach {
            registers.reset()

            registers.setValue(it.r2, 1)

            performProgram(byteArrayOf(it.opcode))

            registers.assertRegister(1, it.r1)
        }
    }

    @Test
    fun opcode_LD_SP_HL() {
        registers.h = 0x01
        registers.l = 0x01

        performProgram(byteArrayOf(0xF9.toByte()))

        registers.assertRegister(257, "SP")
    }

    @Test
    fun opcode_LD_HL_SP_n() {
        // TODO: Implement opcode
        assertTrue(false)
    }

    @Test
    fun opcode_LD_nn_SP() {
        performProgram(byteArrayOf(0x08, 0x01, 0x01))

        registers.assertRegister(257, "SP")
    }

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

            performProgram(byteArrayOf(pushValue.opcode))

            var stackValue = memoryMap.readByte(registers.sp)
            assertEquals(0x02, stackValue)

            stackValue = memoryMap.readByte(registers.sp + 1)
            assertEquals(0x01, stackValue)
        }
    }

    internal data class PopOpcode(val opcode: Byte, val r1: Char, val r2: Char)

    private val popValues = arrayOf(
        PopOpcode(0xF1.toByte(), 'A','F'),
        PopOpcode(0xC1.toByte(), 'B','C'),
        PopOpcode(0xD1.toByte(), 'D','E'),
        PopOpcode(0xE1.toByte(), 'H','L')
    )

    @Test
    fun opcode_POP_nn() {
        popValues.forEach { popValue ->
            registers.reset()

            registers.decreaseStackPointer()
            memoryMap.writeByte(registers.sp, 0x1)
            registers.decreaseStackPointer()
            memoryMap.writeByte(registers.sp, 0x2)

            performProgram(byteArrayOf(popValue.opcode))

            registers.assertRegister(0x2, popValue.r1)
            registers.assertRegister(0x1, popValue.r2)
        }
    }

    private fun performProgram(program: ByteArray) {
        cpu.run(program)
    }
}
