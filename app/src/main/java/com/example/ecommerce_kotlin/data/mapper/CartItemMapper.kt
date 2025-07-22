package com.example.ecommerce_kotlin.data.mapper

import com.example.ecommerce_kotlin.data.local.entity.CartItemEntity
import com.example.ecommerce_kotlin.domain.model.CartItem
import com.example.ecommerce_kotlin.domain.model.Product

fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        product = Product(
            id = productId,
            title = title,
            imageUrl = imageUrl,
            price = price
        ),
        quantity = quantity
    )
}

fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        productId = product.id,
        title = product.title,
        imageUrl = product.imageUrl,
        price = product.price,
        quantity = quantity
    )
}
