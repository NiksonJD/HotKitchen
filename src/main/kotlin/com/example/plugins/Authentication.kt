package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.dto.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JwtConfig.secret))
                    .withAudience(JwtConfig.audience)
                    .withIssuer(JwtConfig.issuer)
                    .build()
            )

            validate { credential ->
                val email = credential.payload.getClaim("email").asString()
                val expiration = credential.payload.getClaim("exp").asLong()
                if (email.isNotBlank() && expiration != null && expiration > System.currentTimeMillis() / 1000) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ -> call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired") }
        }
    }
}