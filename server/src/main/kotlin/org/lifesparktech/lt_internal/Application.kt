
package org.lifesparktech.lt_internal

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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

    fun getRazorpayEventsCollection() = mongoDatabase.getCollection<RazorpayEvent>("razorpayEvents")

    routing {
        route("") {
            get("/") {
                val collection = getRazorpayEventsCollection()
                val filter = Filters.eq(RazorpayEvent::status.name, "captured")
                val projection = Projections.fields(
                    Projections.include(
                        RazorpayEvent::id.name,
                        RazorpayEvent::email.name,
                        RazorpayEvent::contact.name,
                        RazorpayEvent::status.name,
                        RazorpayEvent::amount.name
                    )
                )
                val events = collection.find(filter).projection(projection).toList()
                call.respond(events)
            }
            get("/{id}") {
                val id = call.parameters["id"] ?: return@get call.respondText("Missing id", status = HttpStatusCode.BadRequest)
                val collection = getRazorpayEventsCollection()
                val filter = Filters.eq("id", id)
                val event = collection.find(filter).firstOrNull() ?: return@get call.respondText("Event not found", status = HttpStatusCode.NotFound)
                call.respond(event)
            }
        }

    }
}

@Serializable
data class RazorpayEvent(
    val id: String,
    val email: String,
    val contact: String,
    val status: String,
    val amount: Long
)
