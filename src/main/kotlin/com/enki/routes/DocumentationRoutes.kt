package com.enki.routes

import io.ktor.application.*
import io.ktor.mustache.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.text.get

fun Route.documentationRouting() {
    route("/docs") {
        get {
            call.respond(MustacheContent("index.hbs", null))
        }
    }
}

fun Application.registerDocumentationRoutes() {
    routing {
        documentationRouting()
    }
}