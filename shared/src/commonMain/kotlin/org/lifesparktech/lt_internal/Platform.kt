package org.lifesparktech.lt_internal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform