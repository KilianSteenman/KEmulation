package com.kiliansteenman.kemulation.android

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun Keyboard(onKeyPressed: (key: Int, isPressed: Boolean) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Key("1") { onKeyPressed(0x1, it) }
            Key("2") { onKeyPressed(0x2, it) }
            Key("3") { onKeyPressed(0x3, it) }
            Key("C") { onKeyPressed(0xC, it) }
        }
        Row {
            Key("4") { onKeyPressed(0x4, it) }
            Key("5") { onKeyPressed(0x5, it) }
            Key("6") { onKeyPressed(0x6, it) }
            Key("D") { onKeyPressed(0xD, it) }
        }
        Row {
            Key("7") { onKeyPressed(0x7, it) }
            Key("8") { onKeyPressed(0x8, it) }
            Key("9") { onKeyPressed(0x9, it) }
            Key("E") { onKeyPressed(0xE, it) }
        }
        Row {
            Key("A") { onKeyPressed(0xA, it) }
            Key("0") { onKeyPressed(0x0, it) }
            Key("B") { onKeyPressed(0xB, it) }
            Key("F") { onKeyPressed(0xF, it) }
        }
    }
}

@Composable
private fun Key(key: String, onPress: (isPressed: Boolean) -> Unit) {
    Box(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    try {
                        onPress(true)
                        awaitRelease()
                    } finally {
                        onPress(false)
                    }
                },
            )
        }
        .size(80.dp)
    ) {
        Text(
            text = key,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}