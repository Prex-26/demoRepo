package org.lifesparktech.lt_internal

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopped
import org.slf4j.LoggerFactory



fun Application.connectToMongoDB(): MongoDatabase {
    val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    val rootLogger = loggerContext.getLogger("org.mongodb.driver")

    rootLogger.setLevel(Level.OFF)

    val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()
    val uri= System.getenv("ktor_mongo_uri")
    val mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(uri))
        .serverApi(serverApi)
        .build()


    val mongoClient = MongoClient.create(mongoClientSettings)

    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return mongoClient.getDatabase("paymentData")
}
