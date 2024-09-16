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
        })
    }
    val mongoDatabase = connectToMongoDB()
    configureAuthentication()
    install(Resources)
    routing {
        get("/getOrders") {
            val collection= mongoDatabase.getCollection<RazorpayEvent>("razorpayEvents")
            val events= collection.find().toList()
            events.forEach { event->
                if(event.status=="captured"&&event.amount>3000000){
                    println("Email: ${event.email} Contact: ${event.contact} Amount: ${event.amount}")
                }
            }

        //            mongoDatabase.
        }

    }
}

data class RazorpayEvent(
    @BsonId
    val id: ObjectId,
    val email: String,
    val contact: String,
    val status: String,
    val amount: Long
)