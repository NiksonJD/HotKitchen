
val kotlin_version: String by project
val logback_version: String by project
val jetbrains_exposed = "0.54.0"

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.jetbrains.exposed:exposed-core:$jetbrains_exposed")
    implementation("org.jetbrains.exposed:exposed-dao:$jetbrains_exposed")
    implementation("org.jetbrains.exposed:exposed-jdbc:$jetbrains_exposed")
    implementation("org.postgresql:postgresql:42.7.3")
}

//dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-stdlib")
//    implementation("io.ktor:ktor-server-core:$ktor_version")
//    implementation("io.ktor:ktor-server-netty:$ktor_version")
//    implementation("ch.qos.logback:logback-classic:1.4.14")
//    implementation("io.ktor:ktor-server-auth:$ktor_version")
//    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
//    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
//    implementation("org.jetbrains.exposed:exposed-core:$jetbrains_exposed")
//    implementation("org.jetbrains.exposed:exposed-dao:$jetbrains_exposed")
//    implementation("org.jetbrains.exposed:exposed-jdbc:$jetbrains_exposed")
//    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
//    implementation("com.zaxxer:HikariCP:5.0.1")
//    implementation("org.postgresql:postgresql:42.7.3")
//}