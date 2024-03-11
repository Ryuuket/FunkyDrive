package com.example.plugins

import freemarker.cache.*
import io.ktor.client.engine.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

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
            call.respond(FreeMarkerContent("home.ftl", data, ""))
        }
        get("/login") {
            call.respond(FreeMarkerContent("login.ftl", mapOf("error" to "none"), ""))
        }
        post("/login") {
            val post = call.receiveParameters()
            val mail = post["email"] ?: return@post call.respondText("Missing email",
                status = HttpStatusCode.BadRequest
            )
            val pwd = post["password"] ?: return@post call.respondText("Missing password",
                status = HttpStatusCode.BadRequest
            )

            if (mail.isEmpty()) {
                return@post call.respond(
                    FreeMarkerContent("login.ftl", mapOf("error" to "email"), "")
                )
            }
            if (pwd.isEmpty()) {
                return@post call.respond(
                    FreeMarkerContent("login.ftl", mapOf("error" to "password"), "")
                )
            }
            call.respondRedirect("/")
        }
    }
}

data class IndexData(val items: List<Int>)

