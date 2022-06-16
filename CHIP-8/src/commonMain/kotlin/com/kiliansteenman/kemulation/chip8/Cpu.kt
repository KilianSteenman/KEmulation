@file:OptIn(ExperimentalUnsignedTypes::class)

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

    fun decrementProgramCounter() {
        _programCounter--
        _programCounter--
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
        state.soundTimer--
    }

    private fun getOpcode(): Short {
        val pc = state.programCounter.toInt()
        return state.memory[pc].toUInt().shl(8).or(state.memory[pc + 1].toUInt()).toShort()
    }

    private val Short.registerX: Int
        get() = this.and(0x0F00).toInt().shr(8)

    private val Short.registerY: Int
        get() = this.and(0x00F0).toInt().shr(4)

    fun executeOpcode(opcode: Short) {
        when {
            opcode == 0x00E0.toShort() -> opcode00E0(opcode)
            opcode == 0x00EE.toShort() -> opcode00EE(opcode)
            opcode.and(0xF000.toShort()) == 0x1000.toShort() -> opcodeFXXX(opcode)
            opcode.and(0xF000.toShort()) == 0x2000.toShort() -> opcode2XXX(opcode)
            opcode.and(0xF000.toShort()) == 0x3000.toShort() -> opcode3XXX(opcode)
            opcode.and(0xF000.toShort()) == 0x4000.toShort() -> opcode4XXX(opcode)
            opcode.and(0xF000.toShort()) == 0x5000.toShort() -> opcode5XXX(opcode)
            opcode.and(0xF000.toShort()) == 0x6000.toShort() -> opcode6XXX(opcode)
            opcode.and(0xF000.toShort()) == 0x7000.toShort() -> opcode7XXX(opcode)
            opcode.and(0xF00F.toShort()) == 0x8000.toShort() -> opcode8XXX(opcode)
            opcode.and(0xF00F.toShort()) == 0x8001.toShort() -> opcode8XX1(opcode)
            opcode.and(0xF00F.toShort()) == 0x8002.toShort() -> opcode8XX2(opcode)
            opcode.and(0xF00F.toShort()) == 0x8003.toShort() -> opcode8XX3(opcode)
            opcode.and(0xF00F.toShort()) == 0x8004.toShort() -> opcode8XX4(opcode)
            opcode.and(0xF00F.toShort()) == 0x8005.toShort() -> opcode8XX5(opcode)
            opcode.and(0xF00F.toShort()) == 0x8006.toShort() -> opcode8XX6(opcode)
            opcode.and(0xF00F.toShort()) == 0x8007.toShort() -> opcode8XX7(opcode)
            opcode.and(0xF00F.toShort()) == 0x800E.toShort() -> opcode8XXE(opcode)
            opcode.and(0xF000.toShort()) == 0x9000.toShort() -> opcode9XXX(opcode)
            opcode.and(0xF000.toShort()) == 0xA000.toShort() -> opcodeAXXX(opcode)
            opcode.and(0xF000.toShort()) == 0xC000.toShort() -> opcodeCXXX(opcode)
            opcode.and(0xF000.toShort()) == 0xD000.toShort() -> opcodeDXXX(opcode)
            opcode.and(0xF0FF.toShort()) == 0xE09E.toShort() -> opcodeEX9E(opcode)
            opcode.and(0xF0FF.toShort()) == 0xE0A1.toShort() -> opcodeEXA1(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF007.toShort() -> opcodeFXX7(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF00A.toShort() -> opcodeFXXA(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF015.toShort() -> opcodeFX15(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF018.toShort() -> opcodeFX18(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF01E.toShort() -> opcodeFX1E(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF029.toShort() -> opcodeFX29(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF033.toShort() -> opcodeFX33(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF055.toShort() -> opcodeFX55(opcode)
            opcode.and(0xF0FF.toShort()) == 0xF065.toShort() -> opcodeFX65(opcode)
            else -> TODO("Not yet implemented ${opcode.toUShort().toString(16)}")
        }
    }

    private fun opcode00E0(opcode: Short) {
        display.clear()
    }

    private fun opcode00EE(opcode: Short) {
        state.setProgramCounter(state.stack.removeFirst())
    }

    private fun opcode2XXX(opcode: Short) {
        state.stack.addFirst(state.programCounter)
        state.setProgramCounter(opcode.and(0x0FFF))
    }

    private fun opcodeFXXX(opcode: Short) {
        state.setProgramCounter(opcode.and(0x0FFF))
    }

    private fun opcode3XXX(opcode: Short) {
        val register = opcode.registerX
        val value = opcode.and(0x00FF).toUByte()
        if (state.registers[register] == value) {
            state.increaseProgramCounter()
        }
    }

    private fun opcode4XXX(opcode: Short) {
        val register = opcode.registerX
        val value = opcode.and(0x00FF).toUByte()
        if (state.registers[register] != value) {
            state.increaseProgramCounter()
        }
    }

    private fun opcode5XXX(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        if (state.registers[registerX] == state.registers[registerY]) {
            state.increaseProgramCounter()
        }
    }

    private fun opcode6XXX(opcode: Short) {
        val register = opcode.registerX
        val value = opcode.and(0x00FF).toUByte()
        state.registers[register] = value
    }

    private fun opcode7XXX(opcode: Short) {
        val register = opcode.and(0x0F00.toShort()).toInt().shr(8)
        val value = opcode.and(0x00FF.toShort()).toUByte()
        state.registers[register] = (state.registers[register] + value).toUByte()
    }

    private fun opcode8XXX(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        state.registers[registerX] = state.registers[registerY]
    }

    private fun opcode8XX1(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        state.registers[registerX] = state.registers[registerX].or(state.registers[registerY])
    }

    private fun opcode8XX2(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        state.registers[registerX] = state.registers[registerX].and(state.registers[registerY])
    }

    private fun opcode8XX3(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        state.registers[registerX] = state.registers[registerX].xor(state.registers[registerY])
    }

    private fun opcode8XX4(opcode: Short) {
        state.registers[0xF] = 0.toUByte()

        val registerX = opcode.registerX
        val registerY = opcode.registerY
        val valueX = state.registers[registerX]
        state.registers[registerX] = valueX.plus(state.registers[registerY]).toUByte()
        state.registers[0xF] = if (state.registers[registerX] < valueX) 0x1.toUByte() else 0x0.toUByte()
    }

    private fun opcode8XX6(opcode: Short) {
        val registerX = opcode.registerX
        val valueX = state.registers[registerX]
        state.registers[registerX] = valueX.toUInt().shr(1).toUByte()
    }

    private fun opcode8XX7(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        val valueX = state.registers[registerX]
        val valueY = state.registers[registerY]

        if (valueY > valueX) {
            state.registers[0xF] = 1.toUByte()
        }
        state.registers[registerX] = valueX.minus(valueY).toUByte()
    }

    private fun opcode8XX5(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        val valueX = state.registers[registerX]
        val valueY = state.registers[registerY]

        if (valueY > valueX) {
            state.registers[0xF] = 1.toUByte()
        }
        state.registers[registerX] = valueX.minus(valueY).toUByte()
    }

    private fun opcode8XXE(opcode: Short) {
        val registerX = opcode.registerX
        val valueX = state.registers[registerX]
        state.registers[registerX] = valueX.toUInt().shl(1).toUByte()
    }

    private fun opcode9XXX(opcode: Short) {
        val registerX = opcode.registerX
        val registerY = opcode.registerY
        if (state.registers[registerX] != state.registers[registerY]) {
            state.increaseProgramCounter()
        }
    }

    private fun opcodeAXXX(opcode: Short) {
        state.index = opcode.and(0x0FFF)
    }

    private fun opcodeCXXX(opcode: Short) {
        val registerX = opcode.registerX
        val maxValue = opcode.and(0x00FF).toUByte()
        state.registers[registerX] = rng.nextInt(maxValue.toInt()).toUByte()
    }

    private fun opcodeDXXX(opcode: Short) {
        val rowCount = opcode.and(0x000F).toInt()

        state.registers[0xF] = 0.toUByte()

        val xOffset = state.registers[opcode.registerX].toInt()
        val yOffset = state.registers[opcode.registerY].toInt()
        for (row in 0 until rowCount) {
            val y = (yOffset + row) % display.height
            var sprite = state.memory[state.index + row]

            for (column in 0 until 8) {
                if (sprite.and(0x80.toUByte()) != 0.toUByte()) {
                    val x = (xOffset + column) % display.width
                    val pixelIndex = y * display.width + x
                    if (display.pixels[pixelIndex]) {
                        state.registers[0xF] = 1.toUByte()
                    }

                    display.pixels[pixelIndex] = display.pixels[pixelIndex].xor(true)
                }
                sprite = sprite.toUInt().shl(1).toUByte()
            }
        }
    }

    private fun opcodeEX9E(opcode: Short) {
        val register = opcode.registerX
        if (input.isKeyPressed(state.registers[register])) {
            state.increaseProgramCounter()
        }
    }

    private fun opcodeEXA1(opcode: Short) {
        val register = opcode.registerX
        if (!input.isKeyPressed(state.registers[register])) {
            state.increaseProgramCounter()
        }
    }

    private fun opcodeFXX7(opcode: Short) {
        val register = opcode.registerX
        state.registers[register] = state.delayTimer
    }

    private fun opcodeFXXA(opcode: Short) {
        val register = opcode.registerX
        val (isPressed, key) = input.getPressedKey()
        if (isPressed) {
            state.registers[register] = key
        } else {
            state.decrementProgramCounter()
        }
    }

    private fun opcodeFX18(opcode: Short) {
        val register = opcode.registerX
        state.soundTimer = state.registers[register]
    }

    private fun opcodeFX15(opcode: Short) {
        val register = opcode.registerX
        state.delayTimer = state.registers[register]
    }

    private fun opcodeFX1E(opcode: Short) {
        val register = opcode.registerX
        state.index = (state.index + state.registers[register].toShort()).toShort()
    }

    private fun opcodeFX29(opcode: Short) {
        val register = opcode.registerX
        state.index = (FONT_OFFSET + (state.registers[register] * 5.toUInt())).toShort()
    }

    private fun opcodeFX33(opcode: Short) {
        val register = opcode.registerX
        val value = state.registers[register].toInt()
        state.memory[state.index.toInt()] = (value / 100).toUByte()
        state.memory[state.index.toInt() + 1] = (value / 10 % 10).toUByte()
        state.memory[state.index.toInt() + 2] = (value % 100 % 10).toUByte()
    }


    private fun opcodeFX55(opcode: Short) {
        val registerIndex = opcode.registerX
        for (i in 0..registerIndex) {
            state.memory[state.index + i] = state.registers[i]
        }
    }

    private fun opcodeFX65(opcode: Short) {
        val registerIndex = opcode.registerX
        for (i in 0..registerIndex) {
            state.registers[i] = state.memory[state.index + i]
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