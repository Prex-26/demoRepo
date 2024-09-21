package org.lifesparktech.lt_internal

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

fun Application.configureRazorpay(mongoDatabase: MongoDatabase) {
    routing {
        route("orders") {
            get("/") {
                val collection = mongoDatabase.getCollection<RazorpayEvent>("razorpayEvents")
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
                val collection = mongoDatabase.getCollection<RazorpayEvent>("razorpayEvents")
                val filter = Filters.eq("id", id)
                val event = collection.find(filter).firstOrNull() ?: return@get call.respondText("Event not found", status = HttpStatusCode.NotFound)
                call.respond(event)
            }
        }
    }
}
@Serializable
data class RazorpayEvent(
    @SerialName("_id")
    @Contextual val id: ObjectId,
    val email: String,
    val contact: String,
    val status: String,
    val amount: Long
)


