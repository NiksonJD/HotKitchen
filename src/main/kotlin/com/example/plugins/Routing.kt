package com.example.plugins

import com.example.routes.categoryRoutes
import com.example.routes.mealRoutes
import com.example.routes.orderRoutes
import com.example.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        userRoute()
        mealRoutes()
        categoryRoutes()
        orderRoutes()
    }
}