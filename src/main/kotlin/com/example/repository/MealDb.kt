package com.example.repository

import com.example.models.data.Meal
import com.example.models.data.Meals.mealId
import com.example.models.dto.MealDTO
import org.jetbrains.exposed.sql.transactions.transaction

object MealDb {
    fun addMeal(mealDTO: MealDTO) {
        transaction {
            Meal.new {
                mealId = mealDTO.mealId
                title = mealDTO.title
                price = mealDTO.price
                imageUrl = mealDTO.imageUrl
                categoryIds = mealDTO.categoryIds.joinToString(",")
            }
        }
    }

    fun getAllMeals(): List<MealDTO> = transaction {
        Meal.all().map { it.toMealDTO() }
    }

    fun getMealById(id: Int): MealDTO? = transaction {
        Meal.find { mealId eq id }.firstOrNull()?.toMealDTO()
    }

    private fun Meal.toMealDTO(): MealDTO {
        return MealDTO(
            mealId = this.mealId,
            title = this.title,
            price = this.price,
            imageUrl = this.imageUrl,
            categoryIds = this.categoryIds.split(",").map { it.toInt() }
        )
    }
}