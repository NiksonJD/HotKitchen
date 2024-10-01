package com.example.repository

import com.example.models.data.*
import com.example.models.dto.OrderDTO
import com.example.plugins.BadRequestException
import org.jetbrains.exposed.sql.transactions.transaction

object OrderDb {

    fun createOrderDTO(order: Order): OrderDTO = OrderDTO(
        orderId = order.id.value,
        userEmail = order.userEmail,
        mealsIds = order.mealsIds.split(",").mapNotNull { it.toIntOrNull() },
        price = order.price,
        address = order.address,
        status = order.status
    )

    fun addOrder(profile: Profile, mealsIndex: List<Int>): Order = transaction {
        val totalPrice = calculateTotalPrice(mealsIndex) ?: throw BadRequestException(null)
        Order.new {
            userEmail = profile.email
            mealsIds = mealsIndex.joinToString(",")
            price = totalPrice
            address = profile.address ?: ""
            status = "COOK"
        }
    }

    private fun calculateTotalPrice(mealsIds: List<Int>): Float? = transaction {
        Meal.find { Meals.mealId inList mealsIds }.let { meals ->
            if (meals.count().toInt() == mealsIds.size) meals.sumOf { it.price.toDouble() }.toFloat() else null
        }
    }

    fun markOrderReady(orderId: Int) = transaction {
        Order.findById(orderId)?.apply { status = "COMPLETE" } ?: throw BadRequestException(null)
    }

    fun getOrderHistory(): List<OrderDTO> = transaction {
        Order.all().map(OrderDb::createOrderDTO)
    }

    fun getIncompleteOrders(): List<OrderDTO> = transaction {
        Order.find { Orders.status eq "COOK" }.map(OrderDb::createOrderDTO)
    }
}