package org.lifesparktech.lt_internal

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureAuthentication() {

    routing {
        get("/login") {
        }
    }
}

