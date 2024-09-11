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
}

fun Application.connectToMongoDB(): MongoDatabase {

    val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()
    val uri= System.getenv("ktor_mongo_uri")
    println(uri)
    val mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .serverApi(serverApi)
        .build()



    val mongoClient = MongoClient.create(mongoClientSettings)
    val database = mongoClient.getDatabase("sample_mflix")
//    runBlocking{
//        database.createCollection("testing")
//    }
    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
