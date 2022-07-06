package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeIncTest : OpcodeBaseTest() {

    private val opcodes = arrayOf(
        0x3C.toByte() to "A",
        0x04.toByte() to "B",
        0x0C.toByte() to "C",
        0x14.toByte() to "D",
        0x1C.toByte() to "E",
        0x24.toByte() to "H",
        0x2C.toByte() to "L"
    )

    @Test
    fun register_value_is_increased() {
        opcodes.forEach { (opcode, register) ->
            registers.reset()

            registers.setValue(register, 0)

            performOpcode(byteArrayOf(opcode))

            registers.assertRegister(1, register)
        }
    }
}
