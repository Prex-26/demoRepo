package org.lifesparktech.lt_internal.auth

import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication

val ApplicationCall.user get() = authentication.principal<User>()

val testUser = User(1, "Test", listOf("Egypt", "Austria"))