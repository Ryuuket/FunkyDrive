package com.example.plugins

import com.example.models.user.CreateUserDto
import com.example.models.user.LoginRequestDto
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.service.AuthService
import com.example.models.user.User
import io.ktor.http.*
import io.ktor.server.request.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


fun Application.configureRouting(dbConnection: Connection) {
    val authService = AuthService(dbConnection)

    routing {
//        // Static plugin. Try to access `/static/index.html`
//        static("/static") {
//            resources("static")
//        }

        get("/") {
            call.respondText("Hello World!")
        }

        // User registration route
        // Route d'inscription
        post("/register") {
            val user = call.receive<CreateUserDto>()
            try {
                val userId = authService.createUser(user)
                call.respond(HttpStatusCode.Created, mapOf("userId" to userId))
            } catch (cause: Exception) {
                // Gérer toute erreur survenue lors de la création de l'utilisateur
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequestDto>() // Vous devez créer ce DTO pour les informations de connexion
            try {
                val user = authService.authenticateUser(loginRequest.email, loginRequest.password)
                if (user != null) {
                    call.respond(HttpStatusCode.OK, mapOf("userId" to user.toString())) // Envoyez la réponse avec l'ID de l'utilisateur authentifié
                } else {
                    call.respond(HttpStatusCode.Unauthorized) // Renvoyez une erreur 401 si l'authentification a échoué
                }
            } catch (cause: Exception) {
                call.respond(HttpStatusCode.InternalServerError) // Renvoyez une erreur 500 si une exception est survenue
            }
        }

    }
}
fun Application.connectToPostgres(embedded: Boolean): Connection {
    Class.forName("org.postgresql.Driver")
    if (embedded) {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
    } else {
        val url = "jdbc:postgresql://localhost:5433/Database"
        val user = "useradmin"
        val password = "password"
        println("URL: $url")


        return DriverManager.getConnection(url, user, password)
    }
}
