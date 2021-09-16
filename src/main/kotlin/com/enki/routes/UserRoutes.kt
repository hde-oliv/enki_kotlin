package com.enki.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRouting() {
    route("/") {
        get {
            call.respondText("Hey ya!")
        }
    }
}

fun Application.registerUserRoutes() {
    routing {
        userRouting()
    }
}