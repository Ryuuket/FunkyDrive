package com.example.service

import com.example.models.user.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.Statement
import java.util.UUID

class AuthService(private val connection: Connection) {

    companion object {
        private const val CREATE_TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS users (id UUID PRIMARY KEY, email VARCHAR(255), password VARCHAR(255));"

        private const val INSERT_USER =
            "INSERT INTO users ( email, password) VALUES ( ?, ?, ?) RETURNING id;"

        private const val SELECT_USER_BY_USERNAME_PASSWORD =
            "SELECT id FROM auth WHERE username = ? AND password = ?;"

        private const val DELETE_USER = "DELETE FROM users WHERE id = ?;"
    }

    init {
        createTables()
    }

    private fun createTables() {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_USERS)
    }

    // create new user
    suspend fun createUser(user: CreateUserDto): String {
        insertUser(user)
        return "UserId"
    }
    private fun insertUser(user: CreateUserDto): Int {
        val statement = prepareStatement(INSERT_USER)
        statement.setString(1, user.email)
        statement.setString(2, user.password)
//        statement.setDate(3, user.createdAt)
//        statement.setDate(4, user.dateUpdate)
        val resultSet = statement.executeQuery()
        resultSet.next()
        return 0
    }


    // authenticate user
    suspend fun authenticateUser(username: String, password: String): UUID? {
        val statement = prepareStatement(SELECT_USER_BY_USERNAME_PASSWORD)
        statement.setString(1, username)
        statement.setString(2, password)
        val resultSet = statement.executeQuery()
        return if (resultSet.next()) {
            UUID.fromString(resultSet.getString("user_id"))
        } else {
            null
        }
    }

    // delete user
    suspend fun deleteUser(userId: Int): Boolean {
        val statement = prepareStatement(DELETE_USER)
        statement.setInt(1, userId)
        return statement.executeUpdate() > 0
    }

    private fun prepareStatement(query: String): PreparedStatement {
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    }
}
