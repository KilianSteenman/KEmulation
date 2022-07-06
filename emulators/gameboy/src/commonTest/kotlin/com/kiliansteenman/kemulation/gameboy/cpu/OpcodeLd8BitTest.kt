package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeLd8BitTest : OpcodeBaseTest() {

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

            performOpcode(byteArrayOf(it.opcode, 2))

            registers.assertRegister(2, it.register)
        }
    }
}