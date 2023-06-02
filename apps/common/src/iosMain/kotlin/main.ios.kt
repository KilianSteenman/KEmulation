@file:OptIn(ExperimentalResourceApi::class, ExperimentalUnsignedTypes::class)

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
//        showRomPicker()
        runBlocking {
            try {
                val romFile = resource("ibm.ch8").readBytes().toUByteArray()
                chip8.loadRom(romFile)
                chip8.start()
                isRunning = true
            } catch(e : Exception) {
                println("Error loading ibm.ch8 $e")
            }
        }
    }
}

private fun showRomPicker() {
    val documentPicker = UIDocumentPickerViewController(
        documentTypes = emptyList<UTType>(),//listOf(UTType.typeWithFilenameExtension(filenameExtension = "ch8")),
        inMode = UIDocumentPickerMode.UIDocumentPickerModeOpen
    )
    documentPicker.delegate = object : NSObject(), UIDocumentPickerDelegateProtocol {
        override fun `class`(): ObjCClass? {
            TODO("Not yet implemented")
        }

        override fun conformsToProtocol(aProtocol: Protocol?): Boolean {
            TODO("Not yet implemented")
        }

        override fun description(): String? {
            TODO("Not yet implemented")
        }

        override fun hash(): NSUInteger {
            TODO("Not yet implemented")
        }

        override fun isEqual(`object`: Any?): Boolean {
            TODO("Not yet implemented")
        }

        override fun isKindOfClass(aClass: ObjCClass?): Boolean {
            TODO("Not yet implemented")
        }

        override fun isMemberOfClass(aClass: ObjCClass?): Boolean {
            TODO("Not yet implemented")
        }

        override fun isProxy(): Boolean {
            TODO("Not yet implemented")
        }

        override fun performSelector(aSelector: COpaquePointer?, withObject: Any?): Any? {
            TODO("Not yet implemented")
        }

        override fun performSelector(
            aSelector: COpaquePointer?,
            withObject: Any?,
            _withObject: Any?
        ): Any? {
            TODO("Not yet implemented")
        }

        override fun performSelector(aSelector: COpaquePointer?): Any? {
            TODO("Not yet implemented")
        }

        override fun respondsToSelector(aSelector: COpaquePointer?): Boolean {
            TODO("Not yet implemented")
        }

        override fun superclass(): ObjCClass? {
            TODO("Not yet implemented")
        }
    }

    val window = UIApplication.sharedApplication.windows.last() as? UIWindow
    val currentViewController = window?.rootViewController
    currentViewController?.presentViewController(documentPicker, true) {}
}
