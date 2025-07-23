package com.example.ecommerce_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ecommerce_kotlin.domain.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class OrderViewModel @Inject constructor() : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    init {

        _orders.value = listOf(
            Order("2025-07-22", 250.0, listOf(
                Order.Item("Zapatillas", 1),
                Order.Item("Remera", 2)
            )),
            Order("2025-07-21", 100.0, listOf(
                Order.Item("Pantalón", 1)
            ))
        )
    }
}


