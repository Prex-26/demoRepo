package org.lifesparktech.lt_internal

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json


val options= FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault()).setProjectId("lt-internal").build()
val firebaseApp= FirebaseApp.initializeApp(options)
var db= FirestoreClient.getFirestore(firebaseApp)

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    routing {
        get("") {

            var collection = db.collection("new_orders").whereEqualTo("status", "captured").get().get().documents
            var data = mutableListOf<Payment>()
            collection.toList().forEach {
                val payment = Payment.payment(it.data)
                data.add(payment)
            }
            call.respond(data)
        }
    }
}
