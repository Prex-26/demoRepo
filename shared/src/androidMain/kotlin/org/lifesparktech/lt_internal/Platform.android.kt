package org.lifesparktech.lt_internal

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    fun executeCommand(command: String): String {
//        val process = Runtime.getRuntime().exec(command)
//        process.waitFor()
        return "This cannot be performed on Android"
    }
    override suspend fun handleAuthentication(): String {
        return "This cannot be performed on Android"
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()