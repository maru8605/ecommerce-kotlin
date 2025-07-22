package com.example.ecommerce_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerce_kotlin.domain.model.CartItem
import com.example.ecommerce_kotlin.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    val totalPrice: StateFlow<Double> = MutableStateFlow(0.0)

    fun addItem(product: Product) {
        _cartItems.update { currentList ->
            val existing = currentList.find { it.product.id == product.id }
            if (existing != null) {
                currentList.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                currentList + CartItem(product, 1)
            }
        }
        updateTotal()
    }

    fun removeItem(product: Product) {
        _cartItems.update { currentList ->
            currentList.mapNotNull {
                if (it.product.id == product.id) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1)
                    else null
                } else it
            }
        }
        updateTotal()
    }

    fun deleteItem(product: Product) {
        _cartItems.update { currentList ->
            currentList.filterNot { it.product.id == product.id }
        }
        updateTotal()
    }

    private fun updateTotal() {
        val total = _cartItems.value.sumOf { it.product.price.toDouble() * it.quantity }
        (totalPrice as MutableStateFlow).value = total
    }
}
