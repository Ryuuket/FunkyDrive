package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.service.AuthService
import com.example.models.user.User
import io.ktor.http.*
import io.ktor.server.request.*
import java.sql.Connection
import java.sql.DriverManager

fun Application.configureRouting(dbConnection: Connection) {
    val authService = AuthService(dbConnection)

    routing {
//        // Static plugin. Try to access `/static/index.html`
//        static("/static") {
//            resources("static")
//        }

        // login route
        post("/login") {
            val auth = call.receive<User>()

            val userId = authService.authenticateUser(auth.email, auth.password)
            if (userId != null) {
                call.respond(HttpStatusCode.OK, mapOf("userId" to userId))
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }

        // User registration route
        post("/register") {


//            try {
//                val userId = authService.createUser(user, auth)
//                call.respond(HttpStatusCode.Created, mapOf("userId" to userId))
//            } catch (cause: Exception) {
//                call.respond(HttpStatusCode.InternalServerError)
//            }
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
