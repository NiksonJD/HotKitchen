package com.example.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
    val name : String? = null,
    val userType : String,
    val phone : String? = null,
    val email : String,
    val address : String? = null
)