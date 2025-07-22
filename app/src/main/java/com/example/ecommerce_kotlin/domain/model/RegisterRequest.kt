package com.example.ecommerce_kotlin.domain.model

data class RegisterRequest(
    val nombreCompleto: String,
    val email: String,
    val password: String
)
