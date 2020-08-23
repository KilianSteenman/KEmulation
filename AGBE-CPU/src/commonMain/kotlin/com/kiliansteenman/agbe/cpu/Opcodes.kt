package com.kiliansteenman.agbe.cpu

val opcodes: Map<Byte, (r: Registers, arguments: ByteArray) -> Unit> by lazy {
    val opcodeMap = HashMap<Byte, (r: Registers, arguments: ByteArray) -> Unit>()

    opcodeMap[0x06.toByte()] = { r, a -> r.b = a[0] }
    opcodeMap[0x0E] = { r, a -> r.c = a[0] }
    opcodeMap[0x16] = { r, a -> r.d = a[0] }
    opcodeMap[0x1E] = { r, a -> r.e = a[0] }
    opcodeMap[0x26] = { r, a -> r.h = a[0] }
    opcodeMap[0x2E] = { r, a -> r.l = a[0] }

    opcodeMap[0x7f] = { r, a -> r.a = r.a }
    opcodeMap[0x78] = { r, a -> r.a = r.b }
    opcodeMap[0x79] = { r, a -> r.a = r.c }
    opcodeMap[0x7A] = { r, a -> r.a = r.d }
    opcodeMap[0x7B] = { r, a -> r.a = r.e }
    opcodeMap[0x7C] = { r, a -> r.a = r.h }
    opcodeMap[0x7D] = { r, a -> r.a = r.l }

    opcodeMap[0x40] = { r, a -> r.b = r.b }
    opcodeMap[0x41] = { r, a -> r.b = r.c }
    opcodeMap[0x42] = { r, a -> r.b = r.d }
    opcodeMap[0x43] = { r, a -> r.b = r.e }
    opcodeMap[0x44] = { r, a -> r.b = r.h }
    opcodeMap[0x45] = { r, a -> r.b = r.l }

    opcodeMap[0x48] = { r, a -> r.c = r.b }
    opcodeMap[0x49] = { r, a -> r.c = r.c }
    opcodeMap[0x4A] = { r, a -> r.c = r.d }
    opcodeMap[0x4B] = { r, a -> r.c = r.e }
    opcodeMap[0x4C] = { r, a -> r.c = r.h }
    opcodeMap[0x4D] = { r, a -> r.c = r.l }

    opcodeMap[0x50] = { r, a -> r.d = r.b }
    opcodeMap[0x51] = { r, a -> r.d = r.c }
    opcodeMap[0x52] = { r, a -> r.d = r.d }
    opcodeMap[0x53] = { r, a -> r.d = r.e }
    opcodeMap[0x54] = { r, a -> r.d = r.h }
    opcodeMap[0x55] = { r, a -> r.d = r.l }

    opcodeMap[0x58] = { r, a -> r.e = r.b }
    opcodeMap[0x59] = { r, a -> r.e = r.c }
    opcodeMap[0x5A] = { r, a -> r.e = r.d }
    opcodeMap[0x5B] = { r, a -> r.e = r.e }
    opcodeMap[0x5C] = { r, a -> r.e = r.h }
    opcodeMap[0x5D] = { r, a -> r.e = r.l }

    opcodeMap[0x60] = { r, a -> r.h = r.b }
    opcodeMap[0x61] = { r, a -> r.h = r.c }
    opcodeMap[0x62] = { r, a -> r.h = r.d }
    opcodeMap[0x63] = { r, a -> r.h = r.e }
    opcodeMap[0x64] = { r, a -> r.h = r.h }
    opcodeMap[0x65] = { r, a -> r.h = r.l }

    opcodeMap[0x68] = { r, a -> r.l = r.b }
    opcodeMap[0x69] = { r, a -> r.l = r.c }
    opcodeMap[0x6A] = { r, a -> r.l = r.d }
    opcodeMap[0x6B] = { r, a -> r.l = r.e }
    opcodeMap[0x6C] = { r, a -> r.l = r.h }
    opcodeMap[0x6D] = { r, a -> r.l = r.l }
    opcodeMap
}