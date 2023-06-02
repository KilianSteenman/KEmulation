@file:OptIn(ExperimentalUnsignedTypes::class)

package com.kiliansteenman.kemulation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun RomSelectScreen(
    onSelectRomClicked: () -> Unit,
) {
    Column {
        Button(onClick = { onSelectRomClicked() }) {
            Text("Select Chip-8 ROM")
        }
    }
}