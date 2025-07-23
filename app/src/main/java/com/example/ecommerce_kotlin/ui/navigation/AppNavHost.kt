package com.example.ecommerce_kotlin.ui.navigation

import ProductDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce_kotlin.ui.cart.CartScreen
import com.example.ecommerce_kotlin.ui.login.LoginScreen
import com.example.ecommerce_kotlin.ui.register.RegisterScreen
import com.example.ecommerce_kotlin.ui.catalog.CatalogScreen
import com.example.ecommerce_kotlin.ui.splash.SplashScreen
import com.example.ecommerce_kotlin.ui.orders.OrderSummaryScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_kotlin.viewmodel.CartViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce_kotlin.ui.orders.OrderListScreen
import com.example.ecommerce_kotlin.ui.profile.ProfileScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "splash"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable("catalog") {
            CatalogScreen(navController)
        }

        composable("carrito") {
            CartScreen(navController = navController)
        }

        composable("producto/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(productId, navController)
        }

        composable("orderSummary") {
            val cartViewModel: CartViewModel = hiltViewModel()
            val cartItemsState = cartViewModel.cartItems.collectAsStateWithLifecycle()
            val totalState = cartViewModel.totalPrice.collectAsStateWithLifecycle()

            val cartItems = cartItemsState.value
            val total = totalState.value
            OrderSummaryScreen(
                cartItems = cartItems,
                total = total,
                navController = navController,
                onConfirm = {
                    cartViewModel.clearCart()
                }
            )
        }

        composable("perfil") {
            ProfileScreen(
                onShowOrders = {
                    navController.navigate("ordenes")
                }
            )
        }

        composable("ordenes") {
            OrderListScreen()
        }


    }
}
