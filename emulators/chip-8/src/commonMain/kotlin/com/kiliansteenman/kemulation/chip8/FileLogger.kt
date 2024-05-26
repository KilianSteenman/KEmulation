package com.kiliansteenman.kemulation.chip8

expect class FileLogger(
    name: String
) {

    fun log(log: String)

    fun flush()
}