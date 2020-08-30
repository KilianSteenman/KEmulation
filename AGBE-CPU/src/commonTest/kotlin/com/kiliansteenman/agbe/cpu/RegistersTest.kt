package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class RegistersTest {

    private val registers = Registers()

    @Test
    fun SP_init() {
        registers.assertRegister(0xFFFE, "SP")
    }

    @Test
    fun decrease_SP() {
        registers.decreaseStackPointer()

        registers.assertRegister(0xFFFD, "SP")
    }

    @Test
    fun increase_SP() {
        registers.decreaseStackPointer()
        registers.increaseStackPointer()

        registers.assertRegister(0xFFFE, "SP")
    }
}