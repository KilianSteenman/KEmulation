package com.kiliansteenman.kemulation.chip8

import kotlin.experimental.and

class CpuState {
    var programCounter: Short = 0
    var index: Short = 0
    var stack = ArrayDeque<Short>()
    var delayTimer: Byte = 60
    var soundTimer: Byte = 0
    var registers = ByteArray(16) { 0 }
    var memory = ByteArray(4096)
}

class Cpu(
    private val state: CpuState = CpuState(),
    private val display: Display
) {

    init {
        font.copyInto(state.memory, FONT_OFFSET)
    }

    fun loadProgram(program: ByteArray) {
        // TODO: Write tests for this
        program.copyInto(state.memory, PROGRAM_OFFSET)
        state.programCounter = PROGRAM_OFFSET.toShort()
    }

    fun executeProgram() {
        val opcode = getOpcode()
        state.programCounter++
        state.programCounter++
        executeOpcode(opcode)
    }

    private fun getOpcode(): Short {
        return state.memory[state.programCounter.toInt()].toInt().shl(8)
            .or(state.memory[state.programCounter.toInt() + 1].toInt()).toShort()
    }

    fun executeOpcode(opcode: Short) {
        when {
            opcode == 0x00E0.toShort() -> display.clear()
            opcode.and(0xF000.toShort()) == 0x1000.toShort() -> {
                state.programCounter = opcode.and(0x0FFF)
            }
            opcode.and(0xF000.toShort()) == 0x6000.toShort() -> {
                state.registers[opcode.and(0x0F00.toShort()).toInt().shr(8)] = opcode.and(0x00FF.toShort()).toByte()
            }
            opcode.and(0xF000.toShort()) == 0x7000.toShort() -> {
                state.registers[opcode.and(0x0F00.toShort()).toInt().shr(8)] =
                    (state.registers[opcode.and(0x0F00.toShort()).toInt().shr(8)] +
                            opcode.and(0x00FF.toShort()).toByte()).toByte()
            }
            opcode.and(0xF000.toShort()) == 0xA000.toShort() -> {
                state.index = opcode.and(0x0FFF)
            }
            opcode.and(0xD000.toShort()) == 0xD000.toShort() -> {
                val x = state.registers[opcode.and(0x0F00).toInt().shr(16)]
                val y = state.registers[opcode.and(0x00F0).toInt().shr(8)]
                val rows = opcode.and(0x000F).toInt()
                val sprite = state.memory.copyOfRange(state.index.toInt(), state.index.toInt() + rows)
                display.drawSprite(x, y, sprite)
            }
            else -> TODO("Not yet implemented")
        }
    }

    companion object {

        private const val FONT_OFFSET = 0x050
        private const val PROGRAM_OFFSET = 0x200

        private val font = byteArrayOf(
            0xF0.toByte(), 0x90.toByte(), 0x90.toByte(), 0x90.toByte(), 0xF0.toByte(), // 0
            0x20.toByte(), 0x60.toByte(), 0x20.toByte(), 0x20.toByte(), 0x70.toByte(), // 1
            0xF0.toByte(), 0x10.toByte(), 0xF0.toByte(), 0x80.toByte(), 0xF0.toByte(), // 2
            0xF0.toByte(), 0x10.toByte(), 0xF0.toByte(), 0x10.toByte(), 0xF0.toByte(), // 3
            0x90.toByte(), 0x90.toByte(), 0xF0.toByte(), 0x10.toByte(), 0x10.toByte(), // 4
            0xF0.toByte(), 0x80.toByte(), 0xF0.toByte(), 0x10.toByte(), 0xF0.toByte(), // 5
            0xF0.toByte(), 0x80.toByte(), 0xF0.toByte(), 0x90.toByte(), 0xF0.toByte(), // 6
            0xF0.toByte(), 0x10.toByte(), 0x20.toByte(), 0x40.toByte(), 0x40.toByte(), // 7
            0xF0.toByte(), 0x90.toByte(), 0xF0.toByte(), 0x90.toByte(), 0xF0.toByte(), // 8
            0xF0.toByte(), 0x90.toByte(), 0xF0.toByte(), 0x10.toByte(), 0xF0.toByte(), // 9
            0xF0.toByte(), 0x90.toByte(), 0xF0.toByte(), 0x90.toByte(), 0x90.toByte(), // A
            0xE0.toByte(), 0x90.toByte(), 0xE0.toByte(), 0x90.toByte(), 0xE0.toByte(), // B
            0xF0.toByte(), 0x80.toByte(), 0x80.toByte(), 0x80.toByte(), 0xF0.toByte(), // C
            0xE0.toByte(), 0x90.toByte(), 0x90.toByte(), 0x90.toByte(), 0xE0.toByte(), // D
            0xF0.toByte(), 0x80.toByte(), 0xF0.toByte(), 0x80.toByte(), 0xF0.toByte(), // E
            0xF0.toByte(), 0x80.toByte(), 0xF0.toByte(), 0x80.toByte(), 0x80.toByte()  // F
        )
    }
}