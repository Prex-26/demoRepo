import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
    implementation("com.google.firebase:firebase-admin:latest.release")
    implementation(kotlin("reflect"))
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation("io.ktor:ktor-server-content-negotiation:${libs.versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${libs.versions.ktor}")
    implementation("io.ktor:ktor-server-resources:${libs.versions.ktor}")

//    testImplementation(libs.ktor.server.tests)
//    testImplementation(libs.kotlin.test.junit)

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
