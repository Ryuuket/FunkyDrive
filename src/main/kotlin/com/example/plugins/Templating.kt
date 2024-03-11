package com.example.plugins

import freemarker.cache.*
import io.ktor.client.engine.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    routing {
        get("/html-freemarker") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
        }
        get("/home") {
            val data = mapOf("message" to "Welcome to Funky-drive home page!")
            call.respond(FreeMarkerContent("home.ftl",data, ""))
        }
    }
}

data class IndexData(val items: List<Int>)

