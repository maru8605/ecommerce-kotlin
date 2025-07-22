package com.example.ecommerce_kotlin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce_kotlin.ui.login.LoginScreen
import com.example.ecommerce_kotlin.ui.register.RegisterScreen
import com.example.ecommerce_kotlin.ui.catalog.CatalogScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable("catalogo") {
            CatalogScreen(navController)
        }
    }
}
