package com.example.service

import com.example.user.CreateUserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class AuthService(private val connection: Connection) {

    companion object {
        private const val CREATE_TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS USERS (ID SERIAL PRIMARY KEY, EMAIL VARCHAR(255), PASSWORD VARCHAR(255));"

        private const val INSERT_USER =
            "INSERT INTO users (email, password) VALUES (?, ?)"

        private const val SELECT_USER_BY_USERNAME_PASSWORD =
            "SELECT id, email, password FROM users WHERE email = ? AND password = ?"
    }

    init {
        createTablesUsers()
    }

    private fun createTablesUsers() {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_USERS)
    }

    suspend fun createUser(user: CreateUserDto): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, user.email)
        statement.setString(2, user.password)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw SQLException("Unable to retrieve the id of the newly inserted user")
        }
    }

    suspend fun authenticateUser(username: String, password: String): Int? {
        val statement = connection.prepareStatement(SELECT_USER_BY_USERNAME_PASSWORD)
        statement.setString(1, username)
        statement.setString(2, password)
        val resultSet = statement.executeQuery()
        return if (resultSet.next()) {
            resultSet.getInt("id")
        } else {
            null
        }
    }
}
