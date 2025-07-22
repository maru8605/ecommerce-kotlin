package com.example.ecommerce_kotlin.data.repository

import com.example.ecommerce_kotlin.data.remote.ApiService
import com.example.ecommerce_kotlin.domain.model.LoginRequest
import com.example.ecommerce_kotlin.domain.model.UserResponse
import com.example.ecommerce_kotlin.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<UserResponse> {
        return try {
            val response = apiService.login(email, password)
            if (response.isNotEmpty()) {
                Result.success(response.first())
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(request: LoginRequest): Result<UserResponse> {
        return try {
            val response = apiService.register(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

