package org.lifesparktech.lt_internal

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped

fun Application.configureDatabases() {
    val mongoDatabase = connectToMongoDB()
}

fun Application.connectToMongoDB(): MongoClient {

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

    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return mongoClient
}
