package com.example.service

import com.example.user.CreateUserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.util.*

class AuthService(private val connection: Connection) {

    companion object {
        private const val CREATE_TABLE_USERS =

            "CREATE TABLE IF NOT EXISTS USERS (ID SERIAL PRIMARY KEY, EMAIL VARCHAR(255), PASSWORD VARCHAR(255));"

        private const val INSERT_USER =
            "INSERT INTO users ( email, password) VALUES (  ?, ?)"

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

    // create new com.example.user


    suspend fun createUser(user: CreateUserDto): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, user.email)
        statement.setString(2, user.password)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw SQLException("Unable to retrieve the id of the newly inserted article")
        }
    }
    private fun insertUser(user: CreateUserDto): Int {
        val statement = prepareStatement(INSERT_USER)
        statement.setString(1, user.email)
        statement.setString(2, user.password)
//        statement.setDate(3, com.example.user.createdAt)
//        statement.setDate(4, com.example.user.dateUpdate)
        val resultSet = statement.executeQuery()
        resultSet.next()
        return 0
    }


    // authenticate com.example.user
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

    // delete com.example.user
    suspend fun deleteUser(userId: Int): Boolean {
        val statement = prepareStatement(DELETE_USER)
        statement.setInt(1, userId)
        return statement.executeUpdate() > 0
    }

    private fun prepareStatement(query: String): PreparedStatement {
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    }
}
