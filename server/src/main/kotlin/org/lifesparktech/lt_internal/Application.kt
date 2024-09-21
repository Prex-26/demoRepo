
package org.lifesparktech.lt_internal

import io.ktor.serialization.gson.gson
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import java.text.DateFormat

fun main( args: Array<String>) {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
//        json(Json {
//            prettyPrint = true
//            isLenient = true
//            ignoreUnknownKeys = true
//        })
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
    val mongoDatabase = connectToMongoDB()
    configureAuthentication()
    configureRazorpay(mongoDatabase)
    install(Resources)
    routing {

        get("/") {

        }
    }

}

