package com.example.ecommerce_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ecommerce_kotlin.ui.navigation.AppNavHost
import com.example.ecommerce_kotlin.ui.theme.EcommercekotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommercekotlinTheme {
                AppNavHost()
            }
        }
    }
}
