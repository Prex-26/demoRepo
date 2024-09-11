package org.lifesparktech.lt_internal

import com.example.plugins.configureDatabases
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.p
import kotlinx.serialization.json.Json


fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    configureDatabases()
    configureAuthentication()
    install(Sessions) {
        cookie<UserSession>("user_session")
    }
    install(Resources)

    routing {
        get("/") {
        }
        get("/home") {
            val userSession = getSession(call) ?: return@get
            call.respondText("Hello, ${userSession.token}")
        }
        get("/user")
        {
            val userSession = getSession(call) ?: return@get
            call.respondText("Hello, ${userSession.token}")
        }
    }
}

