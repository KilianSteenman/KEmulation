package com.kiliansteenman.agbe.cpu.boot

import com.kiliansteenman.agbe.cpu.Cpu
import com.kiliansteenman.agbe.cpu.MemoryMap
import com.kiliansteenman.agbe.cpu.Registers
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