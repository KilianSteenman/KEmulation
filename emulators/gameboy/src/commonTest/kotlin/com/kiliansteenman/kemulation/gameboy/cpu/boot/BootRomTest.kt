package com.kiliansteenman.kemulation.gameboy.cpu.boot

import com.kiliansteenman.kemulation.gameboy.cpu.Cpu
import com.kiliansteenman.kemulation.gameboy.cpu.MemoryMap
import com.kiliansteenman.kemulation.gameboy.cpu.Registers
import kotlin.test.Test

@ExperimentalStdlibApi
class BootRomTest {

    private val registers = Registers()
    private val memoryMap = MemoryMap()
    private val cpu = Cpu(registers, memoryMap)

    @Test
    fun run_boot_rom() {
        cpu.executeOpcode(BOOT_ROM)
    }
}