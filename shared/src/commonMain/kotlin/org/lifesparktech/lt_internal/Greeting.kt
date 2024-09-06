package org.lifesparktech.lt_internal

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.get

import io.ktor.client.statement.bodyAsText
class Greeting {


    suspend fun greeting(): String {
         val client = HttpClient(){
            install(Resources)
        }
        val response = client.get("https://google.com")
        return response.bodyAsText()
    }
}