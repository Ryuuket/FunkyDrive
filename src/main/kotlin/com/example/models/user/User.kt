package com.example.user
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email : String,
    val password: String
//    val createdAt: Date,
//    val dateUpdate: Date,
)


@Serializable
data class CreateUserDto(
    val email: String,
    val password: String
)

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)