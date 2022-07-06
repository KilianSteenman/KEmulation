package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeLdR1R2Test : OpcodeBaseTest() {

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

    @Test
    fun opcode_LD_r1_r2() {
        ldR1R2Opcodes.forEach {
            registers.reset()

            registers.setValue(it.r2, 1)

            performOpcode(byteArrayOf(it.opcode))

            registers.assertRegister(1, it.r1)
        }
    }
}