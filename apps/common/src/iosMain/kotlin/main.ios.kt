@file:OptIn(
    ExperimentalResourceApi::class, ExperimentalUnsignedTypes::class,
    ExperimentalUnsignedTypes::class
)

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.kiliansteenman.kemulation.chip8.Chip8
import com.kiliansteenman.kemulation.chip8.InputState
import com.kiliansteenman.kemulation.common.KEmulationApp
import com.kiliansteenman.kemulation.common.OnScreenKeyboard
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ObjCClass
import kotlinx.coroutines.runBlocking
import objcnames.classes.Protocol
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import platform.UIKit.UIApplication
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerMode
import platform.UIKit.UIDocumentPickerViewController
import platform.UIKit.UIWindow
import platform.UniformTypeIdentifiers.UTType
import platform.darwin.NSObject
import platform.darwin.NSUInteger

fun MainViewController() = ComposeUIViewController {
    val onScreenKeyboard = OnScreenKeyboard(InputState())
    val chip8 = Chip8(input = onScreenKeyboard)

    var isRunning by remember { mutableStateOf(false) }

    KEmulationApp(chip8, onScreenKeyboard, isRunning) {
        selectRom { romFile ->
            chip8.loadRom(romFile)
            chip8.start()
            isRunning = true
        }
    }
}

private fun selectRom(onRomSelected: (rom: UByteArray) -> Unit) {
    runBlocking {
        try {
            onRomSelected(resource("pong.ch8").readBytes().toUByteArray())
        } catch (e: Exception) {
            println("Error loading ibm.ch8 $e")
        }
    }
}
