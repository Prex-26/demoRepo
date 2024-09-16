package org.lifesparktech.lt_internal

import com.google.api.client.json.webtoken.JsonWebToken.Payload
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.toList
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
    val mongoDatabase = connectToMongoDB()
    configureAuthentication()
    install(Resources)
    routing {
        get("/") {
            val collection= mongoDatabase.getCollection<RazorpayEvent>("razorpayEvents")
            val events= collection.find().toList()
            call.respond(events.toString())

        //            mongoDatabase.
        }

    }
}

data class RazorpayEvent(
    val id: String,
    val entity: String,
    val account_id: String,
    val event: String,
    val version: String,
    val event_id: String,
    val event_time: Long,
    val webhook_id: String,
    val payload: Payload
)