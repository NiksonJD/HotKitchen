package com.example.routes

import com.example.repository.MealDb
import com.example.routes.crudRoutes
import com.example.routes.fetchUserType
import io.ktor.server.routing.*

fun Route.mealRoutes() {
    crudRoutes(
        entityName = "meals",
        fetchUserType = ::fetchUserType,
        addEntity = { MealDb.addMeal(it) },
        getEntityById = { MealDb.getMealById(it) },
        getAllEntities = { MealDb.getAllMeals() }
    )
}