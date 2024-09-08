package org.lifesparktech.lt_internal

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override fun executeCommand(command: String): String {
        val runtime = Runtime.getRuntime()
        val process = runtime.exec(command)
        process.waitFor()
        return process.inputStream.bufferedReader().readText()
    }

}

actual fun getPlatform(): Platform = JVMPlatform()