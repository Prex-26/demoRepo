package org.lifesparktech.lt_internal

interface Platform {
    val name: String
//    fun executeCommand(command: String): String
    suspend fun handleAuthentication() : String
}

expect fun getPlatform(): Platform
