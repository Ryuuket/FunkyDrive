package com.example.plugins

import freemarker.cache.*
import io.ktor.client.engine.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import java.util.regex.Pattern

fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    routing {

        get("/home") {
            val data = mapOf("message" to "Welcome to Funky-drive home page!")
            call.respond(FreeMarkerContent("home.ftl", data, ""))
        }
        get("/login") {
            call.respond(FreeMarkerContent("login.ftl", mapOf("error" to "none"), ""))
        }
        post("/login") {
            val post = call.receiveParameters()
            val mail = post["email"] ?: return@post call.respondText(
                "Missing email",
                status = HttpStatusCode.BadRequest
            )
            val pwd = post["password"] ?: return@post call.respondText(
                "Missing password",
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
        get("/register") {
            call.respond(FreeMarkerContent("register", mapOf("error" to "none"), ""))
        }
        post("/register") {
            val post = call.receiveParameters()

            val mail = post["email"] ?: return@post call.respondText(
                "Missing email",
                status = HttpStatusCode.BadRequest
            )
            if (mail.isEmpty()) {
                return@post call.respond(
                    FreeMarkerContent("register.ftl", mapOf("error" to "email"), "")
                )
            }
            val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
            if (!emailRegex.matches(mail)) {
                return@post call.respond(
                    FreeMarkerContent("register.ftl", mapOf("error" to "invalidEmail"), "")
                )
            }
            val pwd = post["password"] ?: return@post call.respondText(
                "Missing password",
                status = HttpStatusCode.BadRequest
            )
            val passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
            if (!passwordPattern.matcher(pwd).matches()) {
                return@post call.respond(
                    FreeMarkerContent("register.ftl", mapOf("error" to "password"), "")
                )
            }
                val confirmPwd = post["confirmPassword"] ?: return@post call.respondText(
                    "Missing confirmation password",
                    status = HttpStatusCode.BadRequest
                )
                if (pwd != confirmPwd) {
                    return@post call.respond(
                        FreeMarkerContent("register.ftl", mapOf("error" to "passwordMismatch"), "")
                    )
            }
            call.respondRedirect("/home")
        }

    }

}

data class IndexData(val items: List<Int>)

