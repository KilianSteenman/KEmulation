package com.kiliansteenman.kemulation.chip8

import kotlin.experimental.and
import kotlin.random.Random

class CpuState {
    private var _programCounter: Short = 0
    val programCounter: Short
        get() = _programCounter

    var index: Short = 0
    var stack = ArrayDeque<Short>()
    var delayTimer: UByte = 60.toUByte()
    var soundTimer: UByte = 0.toUByte()
    var registers = UByteArray(16) { 0.toUByte() }
    var memory = UByteArray(4096)

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
    private val display: Display,
    private val input: Input,
    private val rng: Random = Random.Default
) {

    init {
        font.copyInto(state.memory, FONT_OFFSET.toInt())
    }

    fun loadProgram(program: UByteArray) {
        // TODO: Write tests for this
        program.copyInto(state.memory, PROGRAM_OFFSET)
        state.setProgramCounter(PROGRAM_OFFSET.toShort())
    }

    fun executeProgram() {
        val opcode = getOpcode()
        state.increaseProgramCounter()
        executeOpcode(opcode)
        state.delayTimer--
    }

    private fun getOpcode(): Short {
        val pc = state.programCounter.toInt()
        return state.memory[pc].toUInt().shl(8)
            .or(state.memory[pc + 1].toUInt()).toShort()
    }

    fun executeOpcode(opcode: Short) {
        when {
            opcode == 0x00E0.toShort() -> {
                display.clear()
            }
            opcode == 0x00EE.toShort() -> {
                state.setProgramCounter(state.stack.removeFirst())
            }
            opcode.and(0xF000.toShort()) == 0x1000.toShort() -> {
                state.setProgramCounter(opcode.and(0x0FFF))
            }
            opcode.and(0xF000.toShort()) == 0x2000.toShort() -> {
                state.stack.addFirst(state.programCounter)
                state.setProgramCounter(opcode.and(0x0FFF))
            }
            opcode.and(0xF000.toShort()) == 0x3000.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                val value = opcode.and(0x00FF).toUByte()
                if (state.registers[register] == value) {
                    state.increaseProgramCounter()
                }
            }
            opcode.and(0xF000.toShort()) == 0x4000.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                val value = opcode.and(0x00FF).toUByte()
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
                val value = opcode.and(0x00FF).toUByte()
                state.registers[register] = value
            }
            opcode.and(0xF000.toShort()) == 0x7000.toShort() -> {
                val register = opcode.and(0x0F00.toShort()).toInt().shr(8)
                val value = opcode.and(0x00FF.toShort()).toUByte()
                state.registers[register] = (state.registers[register] + value).toUByte()
            }
            opcode.and(0xF00F.toShort()) == 0x8000.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                state.registers[registerX] = state.registers[registerY]
            }
            opcode.and(0xF00F.toShort()) == 0x8001.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                state.registers[registerX] = state.registers[registerX].or(state.registers[registerY])
            }
            opcode.and(0xF00F.toShort()) == 0x8002.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                state.registers[registerX] = state.registers[registerX].and(state.registers[registerY])
            }
            opcode.and(0xF00F.toShort()) == 0x8003.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                state.registers[registerX] = state.registers[registerX].xor(state.registers[registerY])
            }
            opcode.and(0xF00F.toShort()) == 0x8004.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                val valueX = state.registers[registerX]
                state.registers[registerX] = valueX.plus(state.registers[registerY]).toUByte()
                state.registers[0xF] = if (state.registers[registerX] < valueX) 0x1.toUByte() else 0x0.toUByte()
            }
            opcode.and(0xF00F.toShort()) == 0x8005.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val registerY = opcode.and(0x00F0).toInt().shr(4)
                val valueX = state.registers[registerX]
                state.registers[registerX] = valueX.minus(state.registers[registerY]).toUByte()
            }
            opcode.and(0xF00F.toShort()) == 0x8006.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val valueX = state.registers[registerX]
                state.registers[registerX] = valueX.toUInt().shr(1).toUByte()
            }
            opcode.and(0xF00F.toShort()) == 0x800E.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val valueX = state.registers[registerX]
                state.registers[registerX] = valueX.toUInt().shl(1).toUByte()
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
            opcode.and(0xF000.toShort()) == 0xC000.toShort() -> {
                val registerX = opcode.and(0x0F00).toInt().shr(8)
                val maxValue = opcode.and(0x00FF).toUByte()
                state.registers[registerX] = rng.nextInt(maxValue.toInt()).toUByte()
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
            opcode.and(0xF0FF.toShort()) == 0xE09E.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                if(input.isKeyPressed(state.registers[register])) {
                    state.increaseProgramCounter()
                }
            }
            opcode.and(0xF0FF.toShort()) == 0xF007.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                state.registers[register] = state.delayTimer
            }
            opcode.and(0xF0FF.toShort()) == 0xF015.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                state.delayTimer = state.registers[register]
            }
            opcode.and(0xF0FF.toShort()) == 0xF029.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                state.index = (FONT_OFFSET + (state.registers[register] * 5.toUInt())).toShort()
            }
            opcode.and(0xF0FF.toShort()) == 0xF033.toShort() -> {
                val register = opcode.and(0x0F00).toInt().shr(8)
                val value = state.registers[register].toInt()
                state.memory[state.index.toInt()] = (value / 100).toUByte()
                state.memory[state.index.toInt() + 1] = (value / 10 % 10).toUByte()
                state.memory[state.index.toInt() + 2] = (value % 100 % 10).toUByte()
            }
            opcode.and(0xF0FF.toShort()) == 0xF055.toShort() -> {
                val registerIndex = opcode.and(0x0F00).toInt().shr(8)
                for (i in 0..registerIndex) {
                    state.memory[state.index + i] = state.registers[i]
                }
            }
            opcode.and(0xF0FF.toShort()) == 0xF065.toShort() -> {
                val registerIndex = opcode.and(0x0F00).toInt().shr(8)
                for (i in 0 until registerIndex) {
                    state.registers[i] = state.memory[state.index + i]
                }
            }
            else -> TODO("Not yet implemented ${opcode.toUShort().toString(16)}")
        }
    }

    companion object {

        private val FONT_OFFSET: UByte = 0x050.toUByte()
        private const val PROGRAM_OFFSET = 0x200

        private val font = ubyteArrayOf(
            0xF0.toUByte(), 0x90.toUByte(), 0x90.toUByte(), 0x90.toUByte(), 0xF0.toUByte(), // 0
            0x20.toUByte(), 0x60.toUByte(), 0x20.toUByte(), 0x20.toUByte(), 0x70.toUByte(), // 1
            0xF0.toUByte(), 0x10.toUByte(), 0xF0.toUByte(), 0x80.toUByte(), 0xF0.toUByte(), // 2
            0xF0.toUByte(), 0x10.toUByte(), 0xF0.toUByte(), 0x10.toUByte(), 0xF0.toUByte(), // 3
            0x90.toUByte(), 0x90.toUByte(), 0xF0.toUByte(), 0x10.toUByte(), 0x10.toUByte(), // 4
            0xF0.toUByte(), 0x80.toUByte(), 0xF0.toUByte(), 0x10.toUByte(), 0xF0.toUByte(), // 5
            0xF0.toUByte(), 0x80.toUByte(), 0xF0.toUByte(), 0x90.toUByte(), 0xF0.toUByte(), // 6
            0xF0.toUByte(), 0x10.toUByte(), 0x20.toUByte(), 0x40.toUByte(), 0x40.toUByte(), // 7
            0xF0.toUByte(), 0x90.toUByte(), 0xF0.toUByte(), 0x90.toUByte(), 0xF0.toUByte(), // 8
            0xF0.toUByte(), 0x90.toUByte(), 0xF0.toUByte(), 0x10.toUByte(), 0xF0.toUByte(), // 9
            0xF0.toUByte(), 0x90.toUByte(), 0xF0.toUByte(), 0x90.toUByte(), 0x90.toUByte(), // A
            0xE0.toUByte(), 0x90.toUByte(), 0xE0.toUByte(), 0x90.toUByte(), 0xE0.toUByte(), // B
            0xF0.toUByte(), 0x80.toUByte(), 0x80.toUByte(), 0x80.toUByte(), 0xF0.toUByte(), // C
            0xE0.toUByte(), 0x90.toUByte(), 0x90.toUByte(), 0x90.toUByte(), 0xE0.toUByte(), // D
            0xF0.toUByte(), 0x80.toUByte(), 0xF0.toUByte(), 0x80.toUByte(), 0xF0.toUByte(), // E
            0xF0.toUByte(), 0x80.toUByte(), 0xF0.toUByte(), 0x80.toUByte(), 0x80.toUByte()  // F
        )
    }
}