import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
var mongo_version="5.1.4"
plugins {
    id("com.google.cloud.tools.appengine") version "latest.release"
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "2.0.20"
    application
}



group = "org.lifesparktech.lt_internal"
version = "1.0.0"
application {
    mainClass.set("org.lifesparktech.lt_internal.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation("io.ktor:ktor-server-auth:${libs.versions.ktor}")
    implementation(libs.firebase.admin)
    implementation(kotlin("reflect"))
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation("io.ktor:ktor-server-content-negotiation:${libs.versions.ktor}")

//    implementation("io.ktor:ktor-serialization-kotlinx-json:${libs.versions.ktor}")
    implementation("io.ktor:ktor-serialization-gson:${libs.versions.ktor}")

    implementation("io.ktor:ktor-server-resources:${libs.versions.ktor}")
    implementation("io.ktor:ktor-client-content-negotiation:${libs.versions.ktor}")
    implementation("io.ktor:ktor-server-auth-jwt:${libs.versions.ktor}")
    implementation("io.ktor:ktor-server-auth:${libs.versions.ktor}")


    implementation(libs.okhttp)

    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:$mongo_version")
    implementation("org.mongodb:mongodb-driver-core:$mongo_version")
    implementation("org.mongodb:mongodb-driver-sync:$mongo_version")

    implementation(libs.bson)

    implementation("org.mongodb:bson-kotlinx:$mongo_version")

//    //Koin Dependency Injection
//    implementation("io.insert-koin:koin-ktor:3.5.3")
//    implementation("io.insert-koin:koin-logger-slf4j:3.5.3")


    implementation("io.ktor:ktor-server-html-builder:${libs.versions.ktor}")
    testImplementation(libs.kotlin.test.junit)

}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}
configure<AppEngineAppYamlExtension> {
    stage {
        setArtifact("build/libs/${project.name}-all.jar")
    }
    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "lt-internal"
    }
}
