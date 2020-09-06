package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodeAddSpNTest : OpcodeBaseTest() {

    @Test
    fun opcode_ADD_N_to_SP() {
        registers.sp = 0
        performProgram(byteArrayOf(0xE8.toByte(), 0x01))

        registers.assertRegister(0x01, "SP")
    }
}