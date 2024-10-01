package com.example.repository

import com.example.models.data.Categories.categoryId
import com.example.models.data.Category
import com.example.models.dto.CategoryDTO
import org.jetbrains.exposed.sql.transactions.transaction

object CategoryDb {
    fun addCategory(categoryDTO: CategoryDTO) {
        transaction {
            runCatching {
                Category.new {
                    categoryId = categoryDTO.categoryId
                    title = categoryDTO.title
                    description = categoryDTO.description
                }
            }
        }
    }

    fun getAllCategories(): List<CategoryDTO> = transaction {
        Category.all().map { it.toCategoryDTO() }
    }

    fun getCategoryById(id: Int): CategoryDTO? = transaction {
        Category.find { categoryId eq id }.firstOrNull()?.toCategoryDTO()
    }

    private fun Category.toCategoryDTO(): CategoryDTO {
        return CategoryDTO(
            categoryId = this.categoryId,
            title = this.title,
            description = this.description,
        )
    }
}