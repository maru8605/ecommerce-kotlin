package com.example.ecommerce_kotlin.domain.repository

import com.example.ecommerce_kotlin.domain.model.LoginRequest
import com.example.ecommerce_kotlin.domain.model.RegisterRequest
import com.example.ecommerce_kotlin.domain.model.UserResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserResponse>
    suspend fun register(request: RegisterRequest): Result<UserResponse>
}
