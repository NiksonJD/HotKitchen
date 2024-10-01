package com.example.utils

import io.ktor.http.*

val VALID_EMAIL_PATTERN = Regex("^[a-zA-Z0-9]+[a-zA-Z0-9._%+-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
val VALID_PASSWORD_PATTERN = Regex("(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{6,}")

val OK = HttpStatusCode.OK
