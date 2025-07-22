package com.example.ecommerce_kotlin.domain.repository

import com.example.ecommerce_kotlin.data.remote.RetrofitInstance
import com.example.ecommerce_kotlin.domain.model.LoginRequest
import com.example.ecommerce_kotlin.domain.model.UserResponse
import com.example.ecommerce_kotlin.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String): Result<UserResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val users = RetrofitInstance.api.login(email, password)
                val user = users.firstOrNull()
                if (user != null) Result.success(user)
                else Result.failure(Exception("Credenciales incorrectas"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun register(request: LoginRequest): Result<UserResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val user = RetrofitInstance.api.register(request)
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
