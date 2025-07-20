package com.example.ecommerce_kotlin.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Catalog : Screen("catalog")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Orders : Screen("orders")
}
