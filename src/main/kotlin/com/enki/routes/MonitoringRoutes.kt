package com.enki.routes

import com.enki.plugins.appMicrometerRegistry
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.monitoringRouting() {
    route("/metrics-micrometer") {
        get {
            call.respond(appMicrometerRegistry.scrape())
        }
    }
}

fun Application.registerMonitoringRoutes() {
    routing {
        monitoringRouting()
    }
}