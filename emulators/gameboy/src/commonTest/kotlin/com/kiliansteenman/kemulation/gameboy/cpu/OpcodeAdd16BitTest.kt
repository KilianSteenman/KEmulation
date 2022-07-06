package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeAdd16BitTest : OpcodeBaseTest() {

    private val opcodes = arrayOf(
        0x09.toByte() to "BC",
        0x19.toByte() to "DE",
        0x29.toByte() to "HL",
        0x39.toByte() to "SP",
    )

    @Test
    fun opcode_ADD_16_bit_nn() {
        opcodes.forEach { (opcode, register) ->
            registers.reset()

            registers.hl = 300
            registers.setValue(register, 300)

            performOpcode(byteArrayOf(opcode))

            registers.assertRegister(600, "HL")
        }
    }
}