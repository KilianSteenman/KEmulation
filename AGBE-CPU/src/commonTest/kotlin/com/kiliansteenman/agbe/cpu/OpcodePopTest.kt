package com.kiliansteenman.agbe.cpu

import kotlin.test.Test

@ExperimentalStdlibApi
class OpcodePopTest: OpcodeBaseTest() {

    internal data class PopOpcode(val opcode: Byte, val r1: Char, val r2: Char)

    private val popValues = arrayOf(
        PopOpcode(0xF1.toByte(), 'A','F'),
        PopOpcode(0xC1.toByte(), 'B','C'),
        PopOpcode(0xD1.toByte(), 'D','E'),
        PopOpcode(0xE1.toByte(), 'H','L')
    )

    @Test
    fun opcode_POP_nn() {
        popValues.forEach { popValue ->
            registers.reset()

            registers.decreaseStackPointer()
            memoryMap.writeByte(registers.sp, 0x1)
            registers.decreaseStackPointer()
            memoryMap.writeByte(registers.sp, 0x2)

            performProgram(byteArrayOf(popValue.opcode))

            registers.assertRegister(0x2, popValue.r1)
            registers.assertRegister(0x1, popValue.r2)
        }
    }
}