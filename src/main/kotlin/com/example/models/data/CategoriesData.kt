package com.example.models.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Categories : IntIdTable() {
    val categoryId = integer("categoryId").uniqueIndex()
    val title = varchar("title", 255)
    val description = varchar("description", 1024)
}

class Category(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Category>(Categories)

    var categoryId by Categories.categoryId
    var title by Categories.title
    var description by Categories.description
}