package com.kiliansteenman.agbe.cpu

@ExperimentalStdlibApi
class Registers {

    var a: Int = 0
    var b: Int = 0
    var c: Int = 0
    var d: Int = 0
    var e: Int = 0
    var f: Int = 0
    var h: Int = 0
    var l: Int = 0
    var s: Int = 0xFF
    var p: Int = 0xFE

    var bc: Int
        get() {
            return (b and 0xFF).rotateLeft(8) or (c and 0xFF)
        }
        set(value) {
            throw NotImplementedError("Set not implemented for register BC")
        }

    var de: Int
        get() {
            return ((((d and 0xFF).rotateLeft(8)) or (e and 0xFF)))
        }
        set(value) {}

    var hl: Int
        get() {
            return ((((h and 0xFF).rotateLeft(8)) or (l and 0xFF)))
        }
        set(value) {}

    var sp: Int
        get() {
            return (s and 0xFF).rotateLeft(8) or (p and 0xFF)
        }
        set(value) {}

    private var stackPointer: Short = 0
    private var programCounter: Short = 0
    private var flagRegister: Byte = 0
}