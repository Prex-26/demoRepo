package org.lifesparktech.lt_internal

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override fun executeCommand(command: String): String {
        return "This cannot be performed on Web"
    }
}

actual fun getPlatform(): Platform = WasmPlatform()