package com.example.plugins

import com.example.service.AuthService
import com.example.user.CreateUserDto
import freemarker.cache.ClassTemplateLoader
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.util.regex.Pattern

fun Application.configureTemplating(connection: Connection) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    val authService = AuthService(connection)
    routing {

        get("/register") {
            call.respond(FreeMarkerContent("register.ftl", mapOf("error" to "none"), ""))
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
            val email = post["email"] ?:
            return@post call.respondText("Missing email",
                status = HttpStatusCode.BadRequest
            )
            val password = post["password"] ?:
            return@post call.respondText("Missing password",
                status = HttpStatusCode.BadRequest
            )

            // Check if the com.example.com.example.user exists in the database
            val user = withContext(Dispatchers.IO) { authService.authenticateUser(email, password) }

            // If the com.example.com.example.user exists, respond with a successful login message with the com.example.com.example.user ID
            if (user != null) {
                println("User logged in with ID: $user")
                call.respond(HttpStatusCode.OK, "User logged in with ID: $user")
            } else {
                println("Invalid email or password")
                call.respond(HttpStatusCode.Unauthorized, "Invalid email or password")
            }
        }

        post("/register") {
            val post = call.receiveParameters()
            println(post.entries())

            val mail = post["email"] ?: return@post call.respondText("Missing email", status = HttpStatusCode.BadRequest)
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

            val user = withContext(Dispatchers.IO) { authService.createUser(CreateUserDto(mail, pwd)) }

            println("User registered with ID: $user")
            call.respondRedirect("/home")
        }

    }

}



