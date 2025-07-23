package com.example.ecommerce_kotlin.data.remote

import com.example.ecommerce_kotlin.domain.model.LoginRequest
import com.example.ecommerce_kotlin.domain.model.Product
import com.example.ecommerce_kotlin.domain.model.RegisterRequest
import com.example.ecommerce_kotlin.domain.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.*
import retrofit2.Response



interface ApiService {
    @GET("users")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): List<UserResponse>

    @POST("users")
    suspend fun register(
        @Body request: RegisterRequest
    ): UserResponse

    @GET("products")
    suspend fun getProducts(): List<Product>

    @PATCH("users/{id}")
    suspend fun updateUserAvatar(
        @Path("id") userId: String,
        @Body body: Map<String, String> // Solo el campo avatar
    ): Response<Unit>

}
