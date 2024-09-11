package org.lifesparktech.lt_internal

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.cookies.cookies
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class ServerCommunication {

    val client: HttpClient
    init {
        client= HttpClient(){
            install(Resources)
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
        }
        suspend {
            client.cookies("http://0.0.0.0:8080/")
        }
    }
    suspend fun getOrders(): List<Payment> {
        var response=client.get("https://lt-internal.el.r.appspot.com/orders")
//        println(response.bodyAsText())
        val payments: List<Payment> = Json.decodeFromString(response.bodyAsText())
        return payments;
    }

    suspend fun getUser(): String {
        println("trying to get user")
        var response=client.get("https://lt-internal.el.r.appspot.com/user").bodyAsText().toString()
        println(response)
        return response
    }



}
