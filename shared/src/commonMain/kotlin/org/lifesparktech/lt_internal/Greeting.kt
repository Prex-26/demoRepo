package org.lifesparktech.lt_internal

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.get

import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.resources.Resource
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class Greeting {


    suspend fun greeting(): List<Payment> {
         val client = HttpClient(){
             install(Resources)
             install(ContentNegotiation) {
                 json(Json {
                     prettyPrint = true
                     isLenient = true
                 })
             }
        }

        var response=client.get("https://lt-internal.el.r.appspot.com/orders")
//        println(response.bodyAsText())
        val payments: List<Payment> = Json.decodeFromString(response.bodyAsText())

        return payments;
    }
}
