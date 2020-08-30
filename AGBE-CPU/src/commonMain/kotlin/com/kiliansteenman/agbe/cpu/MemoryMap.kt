package com.kiliansteenman.agbe.cpu

class MemoryMap {

    fun readByte(address: Int): Byte {
        return memory[address]
    }

    fun writeByte(address: Int, value: Int) {
        memory[address] = value.toByte()
    }

    private val memory: ByteArray = ByteArray(0xFFFFFF)
}