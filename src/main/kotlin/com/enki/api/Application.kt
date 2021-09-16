package com.enki.api

import com.enki.plugins.*
import com.enki.routes.registerDocumentationRoutes
import com.enki.routes.registerMonitoringRoutes
import com.enki.routes.registerUserRoutes
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSerialization()
    configureTemplating()
    configureMonitoring()
    registerDocumentationRoutes()
    registerUserRoutes()
    registerMonitoringRoutes()
}
