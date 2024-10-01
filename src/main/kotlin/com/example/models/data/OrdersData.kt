package com.example.models.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Orders : IntIdTable() {
    val userEmail = varchar("user_email", 255)
    val mealsIds = text("meals_ids")
    val price = float("price")
    val address = varchar("address", 255)
    val status = varchar("status", 50)
}

class Order(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Order>(Orders)

    var userEmail by Orders.userEmail
    var mealsIds by Orders.mealsIds
    var price by Orders.price
    var address by Orders.address
    var status by Orders.status
}