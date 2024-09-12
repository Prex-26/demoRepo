package org.lifesparktech.lt_internal

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override suspend fun handleAuthentication() : String {
        println("handleAuthentication")
        return "hi"
    }
}

actual fun getPlatform(): Platform = WasmPlatform()