package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

            performProgram(byteArrayOf(opcode))

            registers.assertRegister(0, register)
        }
    }
}
