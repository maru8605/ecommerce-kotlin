package com.example.ecommerce_kotlin.domain.repository

import com.example.ecommerce_kotlin.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}
