package com.kiliansteenman.kemulation.gameboy.cpu

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

    @Test
    fun test_16_bit_registers() {
        registers.setValue("BC", 400)

        assertEquals(400, registers.bc)
    }

    @Test
    fun whenZeroFlagIsSet_ThanZeroFlagIsSet() {
        registers.f = 0b10000000

        assertTrue(registers.isZeroFlagSet())
    }
}