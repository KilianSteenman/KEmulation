package com.kiliansteenman.agbe.cpu

val opcodes = mapOf<Byte, (registers: Registers) -> Unit>(
    0x7f.toByte() to { registers -> registers.a = registers.a },
    0x78.toByte() to { registers -> registers.a = registers.b },
    0x79.toByte() to { registers -> registers.a = registers.c },
    0x7A.toByte() to { registers -> registers.a = registers.d },
    0x7B.toByte() to { registers -> registers.a = registers.e },
    0x7C.toByte() to { registers -> registers.a = registers.h },
    0x7D.toByte() to { registers -> registers.a = registers.l },

    0x40.toByte() to { registers -> registers.b = registers.b },
    0x41.toByte() to { registers -> registers.b = registers.c },
    0x42.toByte() to { registers -> registers.b = registers.d },
    0x43.toByte() to { registers -> registers.b = registers.e },
    0x44.toByte() to { registers -> registers.b = registers.h },
    0x45.toByte() to { registers -> registers.b = registers.l },

    0x48.toByte() to { registers -> registers.c = registers.b },
    0x49.toByte() to { registers -> registers.c = registers.c },
    0x4A.toByte() to { registers -> registers.c = registers.d },
    0x4B.toByte() to { registers -> registers.c = registers.e },
    0x4C.toByte() to { registers -> registers.c = registers.h },
    0x4D.toByte() to { registers -> registers.c = registers.l },

    0x50.toByte() to { registers -> registers.d = registers.b },
    0x51.toByte() to { registers -> registers.d = registers.c },
    0x52.toByte() to { registers -> registers.d = registers.d },
    0x53.toByte() to { registers -> registers.d = registers.e },
    0x54.toByte() to { registers -> registers.d = registers.h },
    0x55.toByte() to { registers -> registers.d = registers.l },

    0x58.toByte() to { registers -> registers.e = registers.b },
    0x59.toByte() to { registers -> registers.e = registers.c },
    0x5A.toByte() to { registers -> registers.e = registers.d },
    0x5B.toByte() to { registers -> registers.e = registers.e },
    0x5C.toByte() to { registers -> registers.e = registers.h },
    0x5D.toByte() to { registers -> registers.e = registers.l },

    0x60.toByte() to { registers -> registers.h = registers.b },
    0x61.toByte() to { registers -> registers.h = registers.c },
    0x62.toByte() to { registers -> registers.h = registers.d },
    0x63.toByte() to { registers -> registers.h = registers.e },
    0x64.toByte() to { registers -> registers.h = registers.h },
    0x65.toByte() to { registers -> registers.h = registers.l },

    0x68.toByte() to { registers -> registers.l = registers.b },
    0x69.toByte() to { registers -> registers.l = registers.c },
    0x6A.toByte() to { registers -> registers.l = registers.d },
    0x6B.toByte() to { registers -> registers.l = registers.e },
    0x6C.toByte() to { registers -> registers.l = registers.h },
    0x6D.toByte() to { registers -> registers.l = registers.l },
)