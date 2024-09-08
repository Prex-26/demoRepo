package org.lifesparktech.lt_internal

import com.example.plugins.configureDatabases
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


val options= FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault()).setProjectId("lt-internal").build()
val firebaseApp= FirebaseApp.initializeApp(options)
var db= FirestoreClient.getFirestore(firebaseApp)

var okHttpClient = OkHttpClient()

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
    configureDatabases()
    install(Resources)

    routing {
//        get("")
//        {
//            val client = OkHttpClient()
//            val mediaType = "application/json".toMediaType()
//            val body = "{\n    \"messaging_product\": \"whatsapp\",\n    \"to\": \"+917208569133\",\n    \"type\": \"template\",\n    \"template\": {\n        \"name\": \"hello_world\",\n        \"language\": {\n            \"code\": \"en_US\"\n        }\n    }\n}".toRequestBody(mediaType)
//            val request = Request.Builder()
//                .url("https://graph.facebook.com/v20.0/383117594885500/messages")
//                .post(body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer EAALnZAozo0ZCYBO5sHt8JNKgaOoxj7geNa9lR8Y3clYyY9RUSmYH6srJAT6PaYS8hqyrBzVphaJkDpQekUvtrovOoDddhbgFy1FwnCMpLAg73vUU9KRzZAUZAZA4BPT0m7fGVaUZA8FiW3ei8ZCffdx77czLApI56RojFZCxz35WWvpvtJOc65XF9ET6FfbtiNZBfxpwXhNZBI8In3xhHmlU6wmcgnJnwZC")
//                .build()
//            val response = client.newCall(request).execute()
//            call.respond(response.body?.string() ?: "No response")
//        }
        get("/orders") {

            var collection = db.collection("new_orders").whereEqualTo("status", "captured").get().get().documents
            var data = mutableListOf<Payment>()
            collection.toList().forEach {
                val payment = Payment.payment(it.data)
                data.add(payment)
            }
            call.respond(data)
        }
        post ("/updateOrderStatus/{id}/{status")
        {
            db.collection("new_orders").whereEqualTo("status", "captured").get().get().documents

            var id = call.parameters["id"]
            println(id)
        }



    }
}
