package org.lifesparktech.lt_internal

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.*

import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.resources.Resource
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class Greeting {


    suspend fun greeting(): String {
         val client = HttpClient(){
             install(Resources)
             install(ContentNegotiation) {
                 json(Json {
                     prettyPrint = true
                     isLenient = true
                 })
             }
             defaultRequest {
                     host = "https://lt-internal.el.r.appspot.com"
                     url { protocol = URLProtocol.HTTP }

             }
        }
        val response = client.get(Payment(
            amount = 4,
            base_amount = 4,
            contact = "test",
            created_at = 4,
            email = "4",
            fee = 4,
            international = true,
            method = "",
            status = "",
            tax = 4,
            order_id = "TODO()"
        ))
        return response.bodyAsText()
    }
}
