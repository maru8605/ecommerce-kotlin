package com.example.ecommerce_kotlin.domain.model

data class Order(
    val date: String,
    val total: Double,
    val items: List<Item>
) {
    data class Item(
        val title: String,
        val quantity: Int
    )
}
