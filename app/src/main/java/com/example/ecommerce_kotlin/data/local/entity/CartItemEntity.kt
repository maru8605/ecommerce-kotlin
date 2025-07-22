package com.example.ecommerce_kotlin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: String,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val quantity: Int
)
