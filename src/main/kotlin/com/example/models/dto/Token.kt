package com.example.models.dto

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.serialization.Serializable
import java.util.*
import java.util.concurrent.TimeUnit

@Serializable
data class Token(val token: String) {
    companion object {
        fun create(userDTO: UserDTO): Token {
            return Token(JwtConfig.makeToken(userDTO))
        }
    }
}

object JwtConfig {
    val secret = "jwt.secret"
    val issuer = "jwt.issuer"
    val audience = "jwt.audience"
    val validity_in_ms = TimeUnit.HOURS.toMillis(10)

    fun makeToken(userDTO: UserDTO): String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("email", userDTO.email)
            .withClaim("userType", userDTO.userType)
            .withExpiresAt(getExpiration())
            .sign(Algorithm.HMAC256(secret))
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + validity_in_ms)
}