package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeLdSpHlTest: OpcodeBaseTest() {

    @Test
    fun opcode_LD_SP_HL() {
        registers.h = 0x01
        registers.l = 0x01

        performOpcode(byteArrayOf(0xF9.toByte()))

        registers.assertRegister(257, "SP")
    }
}