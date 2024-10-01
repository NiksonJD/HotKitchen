package com.example.routes

import com.example.models.data.User
import com.example.models.dto.ProfileDTO
import com.example.models.dto.Token
import com.example.models.dto.UserDTO
import com.example.plugins.*
import com.example.repository.UserDb
import com.example.repository.UserDb.profileDTOByUser
import com.example.utils.OK
import com.example.utils.VALID_EMAIL_PATTERN
import com.example.utils.VALID_PASSWORD_PATTERN
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute() {
    post("/signup") {
        val userDTO = call.jsonToDTO<UserDTO>() ?: throw InvalidCredentialsException()
        validateCredentials(userDTO)?.let { throw InvalidFieldException(it) }
        UserDb.findUser(userDTO)?.let { throw DuplicateUserException() }
        UserDb.addUser(userDTO).also { call.respond(OK, Token.create(userDTO)) }
    }

    post("/signin") {
        val userDTO = call.jsonToDTO<UserDTO>() ?: throw InvalidCredentialsException()
        UserDb.findUser(userDTO)?.let { call.respond(OK, Token.create(userDTO)) } ?: throw InvalidCredentialsException()
    }

    authenticate("auth-jwt") {
        get("/me") {
            val user = fetchUser(call) ?: throw BadRequestException("User not found")
            profileDTOByUser(user)?.let { call.respond(OK, it) } ?: throw BadRequestException(null)
        }

        put("/me") {
            val user = fetchUser(call) ?: throw BadRequestException(null)
            val profileDTO = call.jsonToDTO<ProfileDTO>() ?: throw BadRequestException("Invalid request")
            UserDb.updateProfile(user, profileDTO)?.let { call.respond(OK, profileDTO) }
                ?: throw BadRequestException("Email cannot be changed")
        }

        delete("/me") {
            val user = fetchUser(call) ?: throw NotFoundException()
            UserDb.deleteUser(user).also { call.respond(OK) }
        }

        get("/validate") {
            fetchUser(call)?.let{call.respond(OK, "Hello, ${it.userType} ${it.email}")} ?: throw UnauthorizedException()
        }
    }
}

fun fetchUser(call: ApplicationCall): User? {
    val principal = call.principal<JWTPrincipal>() ?: throw IllegalArgumentException("Unauthorized")
    val email = principal.payload.getClaim("email")?.asString()
    return email?.let { UserDb.findUserByEmail(email) }
}

private fun validateCredentials(userDTO: UserDTO): String? {
    return when {
        !VALID_EMAIL_PATTERN.matches(userDTO.email) -> "Invalid email"
        !VALID_PASSWORD_PATTERN.matches(userDTO.password) -> "Invalid password"
        else -> null
    }
}