package com.example.ecommerce_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.example.ecommerce_kotlin.ui.theme.EcommercekotlinTheme
import com.example.ecommerce_kotlin.ui.navigation.AppNavHost


@AndroidEntryPoint
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
