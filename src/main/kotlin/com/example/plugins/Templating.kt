package plugins

import freemarker.cache.ClassTemplateLoader
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.service.AuthService
import java.sql.Connection

fun Application.configureTemplating(connection: Connection) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    val authService = AuthService(connection)
    routing {
        get("/register") {
            call.respond(FreeMarkerContent("register.ftl", mapOf("data" to IndexData(listOf(1, 2, 3))), ""))
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
    }
}


data class IndexData(val items: List<Int>)
