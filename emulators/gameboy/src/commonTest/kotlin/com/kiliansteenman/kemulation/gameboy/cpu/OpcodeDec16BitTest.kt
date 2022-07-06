package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeDec16BitTest : OpcodeBaseTest() {

    private val opcodes = arrayOf(
        0x0B.toByte() to "BC",
        0x1B.toByte() to "DE",
        0x2B.toByte() to "HL",
        0x3B.toByte() to "SP",
    )

    @Test
    fun register_value_is_increased() {
        opcodes.forEach { (opcode, register) ->
            registers.reset()

            registers.setValue(register, 1)

            performOpcode(byteArrayOf(opcode))

            registers.assertRegister(0, register)
        }
    }
}
