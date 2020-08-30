package com.kiliansteenman.agbe.cpu

@ExperimentalStdlibApi
val opcodes: Map<Byte, (r: Registers, arguments: ByteArray, memory: MemoryMap) -> Unit> by lazy {
    val opcodeMap = HashMap<Byte, (r: Registers, arguments: ByteArray, memory: MemoryMap) -> Unit>()

    opcodeMap[0x06.toByte()] = { r, a, m -> r.b = a[0].toInt() }
    opcodeMap[0x0E] = { r, a, m -> r.c = a[0].toInt() }
    opcodeMap[0x16] = { r, a, m -> r.d = a[0].toInt() }
    opcodeMap[0x1E] = { r, a, m -> r.e = a[0].toInt() }
    opcodeMap[0x26] = { r, a, m -> r.h = a[0].toInt() }
    opcodeMap[0x2E] = { r, a, m -> r.l = a[0].toInt() }

    opcodeMap[0x7f] = { r, a, m -> r.a = r.a }
    opcodeMap[0x78] = { r, a, m -> r.a = r.b }
    opcodeMap[0x79] = { r, a, m -> r.a = r.c }
    opcodeMap[0x7A] = { r, a, m -> r.a = r.d }
    opcodeMap[0x7B] = { r, a, m -> r.a = r.e }
    opcodeMap[0x7C] = { r, a, m -> r.a = r.h }
    opcodeMap[0x7D] = { r, a, m -> r.a = r.l }

    opcodeMap[0x40] = { r, a, m -> r.b = r.b }
    opcodeMap[0x41] = { r, a, m -> r.b = r.c }
    opcodeMap[0x42] = { r, a, m -> r.b = r.d }
    opcodeMap[0x43] = { r, a, m -> r.b = r.e }
    opcodeMap[0x44] = { r, a, m -> r.b = r.h }
    opcodeMap[0x45] = { r, a, m -> r.b = r.l }

    opcodeMap[0x48] = { r, a, m -> r.c = r.b }
    opcodeMap[0x49] = { r, a, m -> r.c = r.c }
    opcodeMap[0x4A] = { r, a, m -> r.c = r.d }
    opcodeMap[0x4B] = { r, a, m -> r.c = r.e }
    opcodeMap[0x4C] = { r, a, m -> r.c = r.h }
    opcodeMap[0x4D] = { r, a, m -> r.c = r.l }

    opcodeMap[0x50] = { r, a, m -> r.d = r.b }
    opcodeMap[0x51] = { r, a, m -> r.d = r.c }
    opcodeMap[0x52] = { r, a, m -> r.d = r.d }
    opcodeMap[0x53] = { r, a, m -> r.d = r.e }
    opcodeMap[0x54] = { r, a, m -> r.d = r.h }
    opcodeMap[0x55] = { r, a, m -> r.d = r.l }

    opcodeMap[0x58] = { r, a, m -> r.e = r.b }
    opcodeMap[0x59] = { r, a, m -> r.e = r.c }
    opcodeMap[0x5A] = { r, a, m -> r.e = r.d }
    opcodeMap[0x5B] = { r, a, m -> r.e = r.e }
    opcodeMap[0x5C] = { r, a, m -> r.e = r.h }
    opcodeMap[0x5D] = { r, a, m -> r.e = r.l }

    opcodeMap[0x60] = { r, a, m -> r.h = r.b }
    opcodeMap[0x61] = { r, a, m -> r.h = r.c }
    opcodeMap[0x62] = { r, a, m -> r.h = r.d }
    opcodeMap[0x63] = { r, a, m -> r.h = r.e }
    opcodeMap[0x64] = { r, a, m -> r.h = r.h }
    opcodeMap[0x65] = { r, a, m -> r.h = r.l }

    opcodeMap[0x68] = { r, a, m -> r.l = r.b }
    opcodeMap[0x69] = { r, a, m -> r.l = r.c }
    opcodeMap[0x6A] = { r, a, m -> r.l = r.d }
    opcodeMap[0x6B] = { r, a, m -> r.l = r.e }
    opcodeMap[0x6C] = { r, a, m -> r.l = r.h }
    opcodeMap[0x6D] = { r, a, m -> r.l = r.l }

    opcodeMap[0x01] = { r, a, m ->
        r.b = a[0].toInt()
        r.c = a[1].toInt()
    }
    opcodeMap[0x11] = { r, a, m ->
        r.d = a[0].toInt()
        r.e = a[1].toInt()
    }
    opcodeMap[0x21] = { r, a, m ->
        r.h = a[0].toInt()
        r.l = a[1].toInt()
    }
    opcodeMap[0x31] = { r, a, m ->
        r.s = a[0].toInt()
        r.p = a[1].toInt()
    }

    opcodeMap[0xF9.toByte()] = { r, a, m ->
        r.s = r.h
        r.p = r.l
    }

    opcodeMap[0xF8.toByte()] = { r, a, m ->
        NotImplementedError("Opcode 0xF8 not implemented")
    }

    opcodeMap[0x08] = { r, a, m ->
        r.s = a[0].toInt()
        r.p = a[1].toInt()
    }

    opcodeMap
}