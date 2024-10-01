package com.example

import com.example.db.configureDatabases
import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    configureJson()
    configureStatusPages()
    configureAuthentication()
    configureDatabases()
    configureRoutes()
}