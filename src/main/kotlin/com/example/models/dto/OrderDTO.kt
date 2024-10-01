package com.example.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderDTO(
    val orderId: Int,
    val userEmail: String,
    val mealsIds: List<Int>,
    val price: Float = 0.0f,
    val address: String,
    val status: String = "IN PROGRESS"
)