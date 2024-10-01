package com.example.routes

import com.example.repository.CategoryDb
import io.ktor.server.routing.*

fun Route.categoryRoutes() {
    crudRoutes(
        entityName = "categories",
        fetchUserType = ::fetchUserType,
        addEntity = { CategoryDb.addCategory(it) },
        getEntityById = { CategoryDb.getCategoryById(it) },
        getAllEntities = { CategoryDb.getAllCategories() }
    )
}