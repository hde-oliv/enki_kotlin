package com.enki.routes

import com.enki.client.fetchIntraInformation
import com.enki.models.UserInfo
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*

fun Route.userRouting() {
    route("/") {
        get {
            call.respondRedirect("/docs", true)
        }
        get ("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val user: UserInfo =
                fetchIntraInformation(id) ?: return@get call.respondText(
                    "No user with id $id",
                    status = HttpStatusCode.NotFound
            );
            call.respond(user)
        }
    }
}

fun Application.registerUserRoutes() {
    routing {
        userRouting()
    }
}
