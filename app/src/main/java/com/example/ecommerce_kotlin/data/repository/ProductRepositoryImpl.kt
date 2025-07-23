package com.example.ecommerce_kotlin.data.repository

import com.example.ecommerce_kotlin.data.remote.ApiService
import com.example.ecommerce_kotlin.domain.model.Product
import com.example.ecommerce_kotlin.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val apiService: ApiService
) : ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val products = apiService.getProducts()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
