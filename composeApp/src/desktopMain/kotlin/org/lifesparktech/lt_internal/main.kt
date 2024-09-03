package org.lifesparktech.lt_internal

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "lt_internal",
    ) {
        App()
    }
}