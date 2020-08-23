package com.kiliansteenman.agbe.cpu

class Registers {

    var a: Byte = 0
    var b: Byte = 0
    var c: Byte = 0
    var d: Byte = 0
    var e: Byte = 0
    var f: Byte = 0
    var h: Byte = 0
    var l: Byte = 0

    private var stackPointer: Short = 0
    private var programCounter: Short = 0
    private var flagRegister: Byte = 0
}