package com.enki.routes

import com.enki.client.fetchIntraInformation
import com.enki.client.UserDTO
import com.enki.models.UserInfo
import com.enki.database.databaseConnection
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*

fun Route.userRouting() {
    route("/") {
        get ("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id.",
                status = HttpStatusCode.BadRequest
            )
            val user: UserDTO = fetchIntraInformation(id)
            if (user.user != null) {
                databaseConnection(user.user)
                call.respond(user.user)
            } else if (user.code == HttpStatusCode.Unauthorized) {
                call.respondText(
                    "Wrong credentials.",
                    status = user.code
                )
            } else {
                call.respondText(
                    "The user $id does not exist.",
                    status = user.code
                )
            }
        }
    }
}

fun Application.registerUserRoutes() {
    routing {
        userRouting()
    }
}
