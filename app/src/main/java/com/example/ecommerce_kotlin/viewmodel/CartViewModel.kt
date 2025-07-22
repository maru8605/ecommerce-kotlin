package com.example.ecommerce_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_kotlin.data.mapper.toCartItem
import com.example.ecommerce_kotlin.data.mapper.toEntity
import com.example.ecommerce_kotlin.data.repository.CartRepository
import com.example.ecommerce_kotlin.domain.model.CartItem
import com.example.ecommerce_kotlin.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    init {
        // Observa el flujo de cartItems de la base de datos
        viewModelScope.launch {
            cartRepository.getCartItems().collect { cartItemEntities ->
                _cartItems.value = cartItemEntities.map { it.toCartItem() }
                updateTotal()
            }
        }
    }

    fun addItem(product: Product) {
        val existing = _cartItems.value.find { it.product.id == product.id }
        if (existing != null) {
            updateItemQuantity(existing, existing.quantity + 1)
        } else {
            val newCartItem = CartItem(product, 1)
            viewModelScope.launch {
                cartRepository.insertItem(newCartItem.toEntity())
            }
        }
        updateTotal()
    }

    fun removeItem(product: Product) {
        val existing = _cartItems.value.find { it.product.id == product.id }
        if (existing != null && existing.quantity > 1) {
            updateItemQuantity(existing, existing.quantity - 1)
        }
        updateTotal()
    }

    fun deleteItem(product: Product) {
        val itemToDelete = _cartItems.value.find { it.product.id == product.id }
        if (itemToDelete != null) {
            viewModelScope.launch {
                cartRepository.deleteItem(itemToDelete.toEntity())
            }
        }
        updateTotal()
    }

    private fun updateItemQuantity(cartItem: CartItem, newQuantity: Int) {
        val updatedItem = cartItem.copy(quantity = newQuantity)
        viewModelScope.launch {
            cartRepository.updateItem(updatedItem.toEntity())
        }
    }

    private fun updateTotal() {
        val total = _cartItems.value.sumOf { it.product.price * it.quantity }
        _totalPrice.value = total
    }
}

