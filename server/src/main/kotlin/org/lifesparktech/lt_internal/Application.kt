package org.lifesparktech.lt_internal

import Payment
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
                var x = it.data.toMap()
                val payment = Payment(
                    amount = x["amount"] as Long,
                    base_amount = x["base_amount"] as Long,
                    contact = x["contact"] as String,
                    created_at = x["created_at"] as Long,
                    description = x["description"] as String?,
                    email = x["email"] as String,
                    fee = x["fee"] as Long,
                    international = x["international"] as Boolean,
                    invoice_id = x["invoice_id"] as String?,
                    method = x["method"] as String,
                    refund_status = x["refund_status"] as String?,
                    reward = x["reward"] as String?,
                    status = x["status"] as String,
                    tax = x["tax"] as Long,
                )
                data.add(payment)

            }
            call.respond(data)
        }
    }
}
