package org.lifesparktech.lt_internal

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable


fun Application.configureAuthentication() {

    routing {
        get("/login") {
            val session = call.sessions.get<UserSession>()
        }
    }
}

suspend fun getSession(
    call: ApplicationCall
): UserSession? {
//    println()
    val userSession: UserSession? = call.sessions.get()
    call.respond(call.sessions)
    if (userSession == null) {
        call.respond(HttpStatusCode.Unauthorized)

        return null
    }
    return userSession
}

@Serializable
data class UserSession(val state: String, val token: String)

