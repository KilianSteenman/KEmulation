package com.kiliansteenman.kemulation.chip8

import java.io.File

actual class FileLogger actual constructor(
    name: String
) {

    private val bw = File(name).bufferedWriter()

    actual fun log(log: String) {
        bw.write(log)
        bw.newLine()
    }

    actual fun flush() {
        bw.flush()
    }
}