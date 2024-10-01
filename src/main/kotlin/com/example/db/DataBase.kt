package com.example.db

import com.example.models.data.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val database =  Database.connect(
        url = "jdbc:postgresql://localhost:5432/HotKitchen",
        driver = "org.postgresql.Driver",
        user = "hyperskill",
        password = "password"
    )

    transaction(database) {
        SchemaUtils.create(Users, Profiles, Meals, Categories, Orders)
    }
}