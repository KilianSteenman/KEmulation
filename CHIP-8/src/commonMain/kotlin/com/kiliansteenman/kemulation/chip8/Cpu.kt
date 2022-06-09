package com.kiliansteenman.kemulation.chip8

import kotlin.experimental.and

class CpuState {
    private var _programCounter: Short = 0
    val programCounter: Short
        get() = _programCounter

    var index: Short = 0
    var stack = ArrayDeque<Short>()
    var delayTimer: Byte = 60
    var soundTimer: Byte = 0
    var registers = ByteArray(16) { 0 }
    var memory = ByteArray(4096)

    fun increaseProgramCounter() {
        _programCounter++
        _programCounter++
    }

    fun setProgramCounter(pc: Short) {
        _programCounter = pc
    }
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
        state.setProgramCounter(PROGRAM_OFFSET.toShort())
    }

    fun executeProgram() {
        val opcode = getOpcode()
        state.increaseProgramCounter()
        executeOpcode(opcode)
    }

    private fun getOpcode(): Short {
        val pc = state.programCounter.toInt()
        return state.memory[pc].toUInt().shl(8)
            .or(state.memory[pc + 1].toUByte().toUInt()).toShort()
    }

    fun executeOpcode(opcode: Short) {
        when {
            opcode == 0x00E0.toShort() -> display.clear()
            opcode.and(0xF000.toShort()) == 0x1000.toShort() -> {
                state.setProgramCounter(opcode.and(0x0FFF))
            }
            opcode.and(0xF000.toShort()) == 0x2000.toShort() -> {
                state.stack.addFirst(state.programCounter)
                state.setProgramCounter(opcode.and(0x0FFF))
            }
            opcode.and(0xF000.toShort()) == 0x3000.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                val value = opcode.and(0x00FF).toByte()
                if (state.registers[register] == value) {
                    state.increaseProgramCounter()
                }
            }
            opcode.and(0xF000.toShort()) == 0x4000.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                val value = opcode.and(0x00FF).toByte()
                if (state.registers[register] != value) {
                    state.increaseProgramCounter()
                }
            }
            opcode.and(0xF000.toShort()) == 0x5000.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                if (state.registers[registerX] == state.registers[registerY]) {
                    state.increaseProgramCounter()
                }
            }
            opcode.and(0xF000.toShort()) == 0x6000.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                val value = opcode.and(0x00FF).toByte()
                state.registers[register] = value
            }
            opcode.and(0xF000.toShort()) == 0x7000.toShort() -> {
                val register = opcode.and(0x0F00.toShort()).toInt().shr(8)
                val value = opcode.and(0x00FF.toShort()).toByte()
                state.registers[register] = (state.registers[register] + value).toByte()
            }
            opcode.and(0xF000.toShort()) == 0x8000.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                state.registers[registerX] = state.registers[registerY]
            }
            opcode.and(0xF000.toShort()) == 0x9000.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                if (state.registers[registerX] != state.registers[registerY]) {
                    state.increaseProgramCounter()
                }
            }
            opcode.and(0xF000.toShort()) == 0xA000.toShort() -> {
                state.index = opcode.and(0x0FFF)
            }
            opcode.and(0xF000.toShort()) == 0xD000.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                println("Register $registerX $registerY ${state.registers.map { it.toString(16) }}")
                val x = state.registers[registerX]
                val y = state.registers[registerY]
                val rows = opcode.and(0x000F).toInt()
                val sprite = state.memory.copyOfRange(state.index.toInt(), state.index.toInt() + rows)
                display.drawSprite(x, y, sprite)
            }
            opcode.and(0xF0FF.toShort()) == 0xF015.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                state.delayTimer = state.registers[register]
            }
            opcode.and(0xF0FF.toShort()) == 0xF029.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                state.index = (FONT_OFFSET + (state.registers[register] * 5)).toShort()
            }
            else -> TODO("Not yet implemented ${opcode.toUShort().toString(16)}")
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