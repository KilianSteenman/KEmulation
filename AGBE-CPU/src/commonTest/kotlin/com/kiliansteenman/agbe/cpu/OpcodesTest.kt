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

            verifyRegister(2, it.register)
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

            verifyRegister(0, it.register)
        }
    }

    @Test
    fun opcode_LD_r1_r2() {
        ldR1R2Opcodes.forEach {
            registers.reset()

            registers.setValue(it.r2, 1)

            performProgram(byteArrayOf(it.opcode))

            verifyRegister(1, it.r1)
        }
    }

    @Test
    fun opcode_LD_SP_HL() {
        registers.h = 0x01
        registers.l = 0x01

        performProgram(byteArrayOf(0xF9.toByte()))

        verifyRegister(257, "SP")
    }

    @Test
    fun opcode_LD_HL_SP_n() {
        // TODO: Implement opcode
        assertTrue(false)
    }

    @Test
    fun opcode_LD_nn_SP() {
        performProgram(byteArrayOf(0x08, 0x01, 0x01))

        verifyRegister(257, "SP")
    }

    private fun Registers.reset() {
        a = 0
        b = 0
        c = 0
        d = 0
        e = 0
        f = 0
        h = 0
        l = 0
    }

    private fun Registers.setValue(register: Char, value: Int) {
        when (register) {
            'A' -> a = value
            'B' -> b = value
            'C' -> c = value
            'D' -> d = value
            'E' -> e = value
            'F' -> f = value
            'H' -> h = value
            'L' -> l = value
            else -> throw IllegalArgumentException("Unknown register $register")
        }
    }

    private fun Registers.getValue(register: Char): Int {
        return when (register) {
            'A' -> a
            'B' -> b
            'C' -> c
            'D' -> d
            'E' -> e
            'F' -> f
            'H' -> h
            'L' -> l
            else -> throw IllegalArgumentException("Unknown register $register")
        }
    }

    private fun Registers.getShortValue(register: String): Int {
        return when (register) {
            "BC" -> bc
            "DE" -> de
            "HL" -> hl
            "SP" -> sp
            else -> throw IllegalArgumentException("Unknown register $register")
        }
    }

    private fun performProgram(program: ByteArray) {
        cpu.run(program)
    }

    private fun verifyRegister(value: Int, register: Char) {
        val registerValue = registers.getValue(register)
        assertEquals(value, registerValue, "Expected register $register to be $value but found $registerValue")
    }

    private fun verifyRegister(value: Int, register: String) {
        val registerValue = registers.getShortValue(register)
        assertEquals(value, registerValue, "Expected register $register to be $value but found $registerValue")
    }
}