package com.kiliansteenman.agbe.cpu

class Cpu(
    private val registers: Registers = Registers(),
    private val memoryMap: MemoryMap
) {

    fun run(program: ByteArray) {
        val operation = opcodes[program[0]]

        val arguments = if(program.size > 1) {
            program.toList().subList(1, program.lastIndex + 1).toByteArray()
        } else {
            ByteArray(0)
        }

        if (operation == null) {
            throw NotImplementedError("Operation 0x${program[0].toString(16)} not implemented")
        } else {
            operation(registers, arguments)
        }
    }
}