package com.example.ecommerce_kotlin.domain.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)