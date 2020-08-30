package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeLd16BitTest: OpcodeBaseTest() {

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
}