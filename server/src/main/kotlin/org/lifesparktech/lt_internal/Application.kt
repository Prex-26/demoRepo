package org.lifesparktech.lt_internal

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.response.*
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    val mongoDatabase = connectToMongoDB()
    configureAuthentication()
    install(Resources)
    routing {
        get("/getOrders") {
            val collection= mongoDatabase.getCollection<RazorpayEvent>("razorpayEvents")
            val events= collection
            call.respond(events)
        }
    }
}
@Serializable
data class RazorpayEvent(
    val email: String,
    val contact: String,
    val status: String,
    val amount: Long
)