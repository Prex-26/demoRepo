package org.lifesparktech.lt_internal.auth

import io.ktor.server.auth.*

data class User(
    val id: Int,
    val name: String,
    val countries: List<String>
) : Principal