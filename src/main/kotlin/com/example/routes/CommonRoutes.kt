package com.example.routes

import com.example.plugins.BadRequestException
import com.example.plugins.ForbiddenAccessException
import com.example.utils.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

suspend inline fun <reified T : Any> ApplicationCall.jsonToDTO(): T? {
    return runCatching { receive<T>() }
        .getOrElse { null }
}

fun fetchUserType(call: ApplicationCall): String? {
    return call.principal<JWTPrincipal>()?.payload?.getClaim("userType")?.asString()
}

inline fun <reified T> Route.crudRoutes(
    entityName: String,
    crossinline fetchUserType: (ApplicationCall) -> String?,
    crossinline addEntity: (T) -> Unit,
    crossinline getEntityById: suspend (Int) -> T?,
    crossinline getAllEntities: suspend () -> List<T>
) {
    authenticate("auth-jwt") {
        post("/$entityName") {
            fetchUserType(call)?.takeIf { it == "staff" }?.let {
                runCatching { addEntity(call.receive()).also { call.respond(OK, it) } }
                    .getOrElse { throw BadRequestException("Invalid request") }
            } ?: throw ForbiddenAccessException()
        }
        get("/$entityName") {
            val entityId = call.request.queryParameters["id"]?.toIntOrNull()
            val result = entityId?.let { id ->
                getEntityById(id) ?: throw BadRequestException("$entityName not found")
            } ?: getAllEntities()
            call.respond(OK, result)
        }
    }
}