package com.example.plugins

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import org.bson.Document
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.config.tryGetString
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun Application.configureDatabases() {
    val mongoDatabase = connectToMongoDB()
//    val carService = CarService(mongoDatabase)
//    routing {
//        // Create car
//        post("/cars") {
//            val car = call.receive<Car>()
//            val id = carService.create(car)
//            call.respond(HttpStatusCode.Created, id)
//        }
//        // Read car
//        get("/cars/{id}") {
//            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
//            carService.read(id)?.let { car ->
//                call.respond(car)
//            } ?: call.respond(HttpStatusCode.NotFound)
//        }
//        // Update car
//        put("/cars/{id}") {
//            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
//            val car = call.receive<Car>()
//            carService.update(id, car)?.let {
//                call.respond(HttpStatusCode.OK)
//            } ?: call.respond(HttpStatusCode.NotFound)
//        }
//        // Delete car
//        delete("/cars/{id}") {
//            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
//            carService.delete(id)?.let {
//                call.respond(HttpStatusCode.OK)
//            } ?: call.respond(HttpStatusCode.NotFound)
//        }
//    }
}

fun Application.connectToMongoDB(): MongoDatabase {

    val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()
    val uri= System.getenv("mongo")
    println(uri)
    val mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .serverApi(serverApi)
        .build()



    val mongoClient = MongoClient.create(mongoClientSettings)
    val database = mongoClient.getDatabase("sample_mflix")
    runBlocking{
        database.createCollection("test")
    }
    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
