package com.example.routes

import com.example.plugins.BadRequestException
import com.example.plugins.ForbiddenAccessException
import com.example.repository.OrderDb
import com.example.repository.UserDb
import com.example.utils.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRoutes() {

    authenticate("auth-jwt") {
        post("/order") {
            val user = fetchUser(call) ?: throw BadRequestException("User not found")
            val profile = UserDb.findProfileByUser(user) ?: throw BadRequestException("User not found")
            val mealsIds = call.jsonToDTO<List<Int>>() ?: throw BadRequestException(null)
            val order = OrderDb.addOrder(profile, mealsIds)
            OrderDb.createOrderDTO(order).also { call.respond(OK, it) }
        }

        post("/order/{orderId}/markReady") {
            fetchUserType(call)?.takeIf { it == "staff" }?.let {
                runCatching {
                    val orderId = call.parameters["orderId"]?.toIntOrNull() ?: throw BadRequestException(null)
                    OrderDb.markOrderReady(orderId).also { call.respond(OK) }
                }.getOrElse { throw BadRequestException(null) }
            } ?: throw ForbiddenAccessException()
        }

        get("/orderHistory") {
            call.respond(OrderDb.getOrderHistory())
        }

        get("/orderIncomplete") {
            call.respond(OrderDb.getIncompleteOrders())
        }
    }
}