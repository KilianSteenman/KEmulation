@file:OptIn(ExperimentalUnsignedTypes::class)

import androidx.compose.runtime.*
import com.kiliansteenman.kemulation.chip8.Cpu
import com.kiliansteenman.kemulation.chip8.CpuState
import com.kiliansteenman.kemulation.chip8.Display
import com.kiliansteenman.kemulation.chip8.InputState
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.get
import org.w3c.files.FileReader
import org.w3c.files.get
import org.w3c.xhr.ProgressEvent

fun main() {
    val body = document.getElementsByTagName("body").get(0) as HTMLElement

    val inputState = InputState()
    val keyboardInput = KeyboardInput(inputState)
    body.addEventListener("keyup", {
        keyboardInput.processKeyboardEvent((it as KeyboardEvent), false)
    })

    body.addEventListener("keydown", {
        keyboardInput.processKeyboardEvent((it as KeyboardEvent), true)
    })

    renderComposable(rootElementId = "root") {
        var file: UByteArray? by remember { mutableStateOf(null) }

        if (file == null) {
            FileSelection { file = it }
        } else {
            Chip8Player(file!!, inputState)
        }
    }
}

@Composable
fun FileSelection(onFileSelected: (UByteArray) -> Unit) {
    Text("Select file")

    Input(type = InputType.File) {
        id("test")
        println("Add event listener 1")
        addEventListener("change") { event ->
            val file = (event.target as HTMLInputElement).files?.get(0)!!

            FileReader().apply {
                onload = { result ->
                    val bytes = (((result as ProgressEvent).target as FileReader).result as ArrayBuffer).toUByteArray()
                    onFileSelected(bytes)
                    null
                }
            }.readAsArrayBuffer(file)
//            val arrayBuffer = FileReaderSync().readAsArrayBuffer(file)
//            selectedFile = "ByteArraySize: ${arrayBuffer.toUByteArray().size}"
            println("selected file ${file.name}")
        }
    }
}

fun ArrayBuffer.toUByteArray(): UByteArray =
    this.run { Int8Array(this).unsafeCast<ByteArray>().map { it.toUByte() }.toUByteArray() }

@Composable
fun Chip8Player(rom: UByteArray, inputState: InputState) {
    val cpuState = CpuState()
    val display = Display(64, 32)
    val cpu = Cpu(state = cpuState, display = display, inputState = inputState)
    cpu.loadProgram(rom)

    var ticks by remember { mutableStateOf(0) }
    var pixels by remember { mutableStateOf(emptyArray<Boolean>()) }
    var playAudio by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(((1f / 60) * 100).toLong())
            cpu.executeProgram()
            pixels = display.pixels.toTypedArray()
            playAudio = cpuState.soundTimer > 0.toUByte()
            ticks++
        }
    }

    if (playAudio) {
//            Toolkit.getDefaultToolkit().beep()
    }

    Div(attrs = {
        style {
            property("font-family", "'Courier New', monospace")
            property("font-size", "10px")
            property("white-space", "pre-wrap")
        }
    }) {
        MonochromeDisplay(pixels = pixels)
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

