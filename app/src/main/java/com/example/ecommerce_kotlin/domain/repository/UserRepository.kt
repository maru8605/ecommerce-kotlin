package com.example.ecommerce_kotlin.domain.repository

import com.example.ecommerce_kotlin.data.remote.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun updateUserAvatar(userId: String, avatarUrl: String) {
        apiService.updateUserAvatar(userId, mapOf("avatar" to avatarUrl))
    }
}
