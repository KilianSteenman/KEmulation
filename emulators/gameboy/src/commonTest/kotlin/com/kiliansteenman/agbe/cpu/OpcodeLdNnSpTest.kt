package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeLdNnSpTest: OpcodeBaseTest() {

    @Test
    fun opcode_LD_nn_SP() {
        performOpcode(byteArrayOf(0x08, 0x01, 0x01))

        registers.assertRegister(257, "SP")
    }
}