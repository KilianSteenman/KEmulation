@file:OptIn(ExperimentalUnsignedTypes::class)

import androidx.compose.runtime.*
import com.kiliansteenman.kemulation.chip8.Chip8
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import org.w3c.files.FileReader
import org.w3c.files.get
import org.w3c.xhr.ProgressEvent

fun main() {
    val body = document.getElementsByTagName("body").get(0) as HTMLElement
    val keyboardInput = KeyboardInput(body)

    renderComposable(rootElementId = "root") {
        var file: UByteArray? by remember { mutableStateOf(null) }

        if (file == null) {
            FileSelection { file = it }
        } else {
            Chip8Player(file!!, keyboardInput)
        }
    }
}

@Composable
fun FileSelection(onFileSelected: (UByteArray) -> Unit) {
    Text("Select file")

    Input(type = InputType.File) {
        addEventListener("change") { event ->
            val file = (event.target as HTMLInputElement).files?.get(0)!!

            FileReader().apply {
                onload = { result ->
                    val bytes = (((result as ProgressEvent).target as FileReader).result as ArrayBuffer).toUByteArray()
                    onFileSelected(bytes)
                    null
                }
            }.readAsArrayBuffer(file)
        }
    }
}

fun ArrayBuffer.toUByteArray(): UByteArray =
    this.run { Int8Array(this).unsafeCast<ByteArray>().map { it.toUByte() }.toUByteArray() }

@Composable
internal fun Chip8Player(rom: UByteArray, keyboardInput: KeyboardInput) {
    val chip8 = Chip8(input = keyboardInput).apply {
        loadRom(rom)
        start()
    }

    Div(attrs = {
        style {
            property("font-family", "'Courier New', monospace")
            property("font-size", "10px")
            property("white-space", "pre-wrap")
        }
    }) {
        val pixels = chip8.pixels.collectAsState(null)

        pixels.value?.let {
            MonochromeDisplay(pixels = it.toTypedArray())
        }
    }
}

@Composable
fun MonochromeDisplay(pixels: Array<Boolean>) {
    pixels.map { isEnabled -> if (isEnabled) "o" else "." }
        .chunked(64)
        .forEach { rowPixels ->
            Div {
                Text(rowPixels.joinToString(""))
            }
        }
}

