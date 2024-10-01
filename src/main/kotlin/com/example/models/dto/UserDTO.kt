package com.example.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val email : String,
    val password : String = "",
    val userType : String = "",
)