package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class RegistersTest {

    private val registers = Registers()

    @Test
    fun SP_init() {
        registers.assertRegister(0xFFFE, "SP")
    }
}