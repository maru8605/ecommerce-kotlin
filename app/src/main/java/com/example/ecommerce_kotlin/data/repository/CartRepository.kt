package com.example.ecommerce_kotlin.data.repository

import com.example.ecommerce_kotlin.data.local.dao.CartDao
import com.example.ecommerce_kotlin.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    fun getCartItems(): Flow<List<CartItemEntity>> = cartDao.getCartItems()

    suspend fun insertItem(item: CartItemEntity) = cartDao.insertCartItem(item)

    suspend fun updateItem(item: CartItemEntity) = cartDao.updateCartItem(item)

    suspend fun deleteItem(item: CartItemEntity) = cartDao.deleteCartItem(item)

    suspend fun clearCart() = cartDao.clearCart()


}
