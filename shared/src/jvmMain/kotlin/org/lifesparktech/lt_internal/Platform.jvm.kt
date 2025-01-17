package org.lifesparktech.lt_internal

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    suspend fun handleAuthentication(): String {
        TODO("Not yet implemented")
    }
}

actual fun getPlatform(): Platform = JVMPlatform()