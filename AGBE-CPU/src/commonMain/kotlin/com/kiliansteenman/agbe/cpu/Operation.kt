package com.kiliansteenman.agbe.cpu

interface Operation {

    operator fun invoke(memoryMap: MemoryMap)
}