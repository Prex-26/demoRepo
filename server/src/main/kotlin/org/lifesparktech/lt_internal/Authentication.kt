package org.lifesparktech.lt_internal

import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserPasswordCredential
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.lifesparktech.lt_internal.auth.JwtConfig
import org.lifesparktech.lt_internal.auth.UserSource
import org.lifesparktech.lt_internal.auth.UserSourceImpl
import org.lifesparktech.lt_internal.auth.user


fun Application.configureAuthentication() {
    val userSource: UserSource = UserSourceImpl()
    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "ktor.io"
            validate {
                it.payload.getClaim("id").asInt()?.let(userSource::findUserById)
            }
        }
    }
    routing {
        post("login") {
            val credentials = call.receive<UserPasswordCredential>()
            val user = userSource.findUserByCredentials(credentials)
            val token = JwtConfig.makeToken(user)
            call.respondText(token)
        }
        authenticate {
            route("secret") {
                get {
                    val user = call.user!!
                    call.respond(user.countries)
                }

            }
        }
    }
}



