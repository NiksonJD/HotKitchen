package com.example.models.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Meals : IntIdTable() {
    val mealId = integer("mealId").uniqueIndex()
    val title = varchar("title", 255)
    val price = float("price")
    val imageUrl = varchar("image_url", 255)
    val categoryIds = text("category_ids")
}

class Meal(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Meal>(Meals)

    var mealId by Meals.mealId
    var title by Meals.title
    var price by Meals.price
    var imageUrl by Meals.imageUrl
    var categoryIds by Meals.categoryIds
}