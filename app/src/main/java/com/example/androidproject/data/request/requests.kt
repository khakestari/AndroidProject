package com.example.androidproject.data.request

// body we send to register api
data class RegisterRequest(
    val email: String,
    val name: String,
    val studentNumber: String,
    val password: String
)