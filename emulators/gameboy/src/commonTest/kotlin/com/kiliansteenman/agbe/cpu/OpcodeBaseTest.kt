package com.kiliansteenman.agbe.cpu

@ExperimentalStdlibApi
open class OpcodeBaseTest {

    protected val registers = Registers()
    protected val memoryMap = MemoryMap()
    private val cpu = Cpu(registers, memoryMap)

    protected fun performOpcode(program: ByteArray) {
        cpu.executeOpcode(program)
    }
}