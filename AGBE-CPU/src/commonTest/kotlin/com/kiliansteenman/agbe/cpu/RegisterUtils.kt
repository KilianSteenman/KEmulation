package com.kiliansteenman.agbe.cpu

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalStdlibApi
fun Registers.assertRegister(value: Int, register: Char) {
    val registerValue = getValue(register)
    assertEquals(value, registerValue, "Expected register $register to be $value but found $registerValue")
}

@ExperimentalStdlibApi
fun Registers.assertRegister(value: Int, register: String) {
    val registerValue = getValue(register)
    assertEquals(value, registerValue, "Expected register $register to be $value but found $registerValue")
}

@ExperimentalStdlibApi
fun Registers.setValue(register: Char, value: Int) {
    when (register) {
        'A' -> a = value
        'B' -> b = value
        'C' -> c = value
        'D' -> d = value
        'E' -> e = value
        'F' -> f = value
        'H' -> h = value
        'L' -> l = value
        else -> throw IllegalArgumentException("Unknown register $register")
    }
}

@ExperimentalStdlibApi
fun Registers.setValue(register: String, value: Int) {
    when (register) {
        "A" -> a = value
        "B" -> b = value
        "C" -> c = value
        "D" -> d = value
        "E" -> e = value
        "F" -> f = value
        "H" -> h = value
        "L" -> l = value
        else -> throw IllegalArgumentException("Unknown register $register")
    }
}

@ExperimentalStdlibApi
private fun Registers.getValue(register: Char): Int {
    return when (register) {
        'A' -> a
        'B' -> b
        'C' -> c
        'D' -> d
        'E' -> e
        'F' -> f
        'H' -> h
        'L' -> l
        else -> throw IllegalArgumentException("Unknown register $register")
    }
}

@ExperimentalStdlibApi
private fun Registers.getValue(register: String): Int {
    return when (register) {
        "A" -> a
        "B" -> b
        "C" -> c
        "D" -> d
        "E" -> e
        "F" -> f
        "H" -> h
        "L" -> l
        "SP" -> sp
        "BC" -> bc
        "DE" -> de
        "HL" -> hl
        else -> throw IllegalArgumentException("Unknown register $register")
    }
}

@ExperimentalStdlibApi
fun Registers.reset() {
    a = 0
    b = 0
    c = 0
    d = 0
    e = 0
    f = 0
    h = 0
    l = 0
}
