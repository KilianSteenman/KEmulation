package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeInc16BitTest : OpcodeBaseTest() {

    private val opcodes = arrayOf(
        0x03.toByte() to "BC",
        0x13.toByte() to "DE",
        0x23.toByte() to "HL",
        0x33.toByte() to "SP",
    )

    @Test
    fun register_value_is_increased() {
        opcodes.forEach { (opcode, register) ->
            registers.reset()

            registers.setValue(register, 0)

            performProgram(byteArrayOf(opcode))

            registers.assertRegister(1, register)
        }
    }
}
