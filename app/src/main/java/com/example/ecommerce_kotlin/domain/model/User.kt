package com.example.ecommerce_kotlin.domain.model

data class User(
    val id: String = "",
    val name: String,
    val email: String,
    val password: String,
    val avatar: String,
    val createdAt: String
)