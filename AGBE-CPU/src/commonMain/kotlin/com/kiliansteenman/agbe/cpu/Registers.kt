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

    var pc: Int = 0

    var bc: Int
        get() {
            return (b and 0xFF).rotateLeft(8) or (c and 0xFF)
        }
        set(value) {
            b = value.rotateRight(8)
            c = value and 0xFF
        }

    var de: Int
        get() {
            return (d and 0xFF).rotateLeft(8) or (e and 0xFF)
        }
        set(value) {
            d = value.rotateRight(8)
            e = value and 0xFF
        }

    var hl: Int
        get() {
            return h.rotateLeft(8) or l
        }
        set(value) {
            h = value.rotateRight(8)
            l = value and 0xFF
        }

    var sp: Int
        get() {
            return (s and 0xFF).rotateLeft(8) or (p and 0xFF)
        }
        set(value) {
            s = value.rotateRight(8)
            p = value and 0xFF
        }

    fun decreaseStackPointer() {
        sp--
    }

    fun increaseStackPointer() {
        sp++
    }

    fun isZeroFlagSet(): Boolean {
        return f and 0b10000000 == 0b10000000
    }

    fun isSubtractFlagSet(): Boolean {
        return f and 0b01000000 == 0b01000000
    }

    fun isHalfCarryFlagSet(): Boolean {
        return f and 0b00100000 == 0b00100000
    }

    fun isCarryFlagSet(): Boolean {
        return f and 0b00010000 == 0b00010000
    }

    fun setSubtractFlag() {
        f = f or 0b01000000
    }
}