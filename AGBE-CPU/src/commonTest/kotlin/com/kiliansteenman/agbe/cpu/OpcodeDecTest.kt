package com.kiliansteenman.agbe.cpu

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalStdlibApi
class OpcodeDecTest : OpcodeBaseTest() {

    private val opcodes = arrayOf(
        0x3D.toByte() to "A",
        0x05.toByte() to "B",
        0x0D.toByte() to "C",
        0x15.toByte() to "D",
        0x1D.toByte() to "E",
        0x25.toByte() to "H",
        0x2D.toByte() to "L"
    )

    @Test
    fun register_value_is_decreased() {
        opcodes.forEach { (opcode, register) ->
            registers.reset()

            registers.setValue(register, 1)

            performProgram(byteArrayOf(opcode))

            registers.assertRegister(0, register)
        }
    }
}
