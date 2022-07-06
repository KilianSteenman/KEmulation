package com.kiliansteenman.kemulation.gameboy.cpu

internal data class MathOperation(
    val opcode: Byte,
    val r1: Char,
    val r2: Char,
    val r1Value: Int,
    val r2Value: Int,
    val outputValue: Int
)