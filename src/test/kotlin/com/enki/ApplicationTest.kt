package com.enki

import io.ktor.server.engine.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.features.*
import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.mustache.Mustache
import io.ktor.mustache.MustacheContent
import io.micrometer.prometheus.*
import io.ktor.metrics.micrometer.*
import org.slf4j.event.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.locations.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.enki.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
//                assertEquals("Hello World!", response.content)
            }
        }
    }
}
