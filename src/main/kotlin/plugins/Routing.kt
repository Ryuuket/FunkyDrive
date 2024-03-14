package plugins

import user.CreateUserDto
import user.LoginRequestDto
import service.AuthService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.DriverManager

fun Application.configureRouting(dbConnection: Connection) {
    val authService = AuthService(dbConnection)

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/register") {
            val user = call.receive<CreateUserDto>()
            try {
                val userId = authService.createUser(user)
                call.respond(HttpStatusCode.Created, mapOf("userId" to userId))
            } catch (cause: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequestDto>()
            try {
                val user = authService.authenticateUser(loginRequest.email, loginRequest.password)
                if (user != null) {
                    call.respond(HttpStatusCode.OK, mapOf("userId" to user.toString()))
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            } catch (cause: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
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
