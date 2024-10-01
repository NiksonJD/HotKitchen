package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

class InvalidCredentialsException : Exception()
class InvalidFieldException(message: String) : Exception(message)
class DuplicateUserException : Exception()
class BadRequestException(message: String?) : Exception(message)
class NotFoundException : Exception()
class UnauthorizedException : Exception()
class ForbiddenAccessException : Exception()

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<InvalidCredentialsException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden, "Invalid email or password")
        }
        exception<InvalidFieldException> { call, cause ->
            call.respond(HttpStatusCode.Forbidden, cause.message ?: "Invalid credentials")
        }
        exception<DuplicateUserException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden, "User already exists")
        }
        exception<ForbiddenAccessException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden, mapOf("status" to "Access denied"))
        }
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "400 Bad Request")
        }
        exception<NotFoundException> { call, _ ->
            call.respond(HttpStatusCode.NotFound, "")
        }
        exception<UnauthorizedException> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized, "")
        }
    }
}