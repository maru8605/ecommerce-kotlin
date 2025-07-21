package com.example.ecommerce_kotlin.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = { Text("Email") },
            isError = emailError,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError) {
            Text("El email es obligatorio", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError,
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError) {
            Text("La contraseña es obligatoria", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                emailError = email.isBlank()
                passwordError = password.isBlank()
                if (!emailError && !passwordError) {
                    // Acá después se llamará al ViewModel
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            // Navegar a registro (más adelante)
            // navController.navigate(Screen.Register.route)
        }) {
            Text("¿No tenés cuenta? Registrate")
        }
    }
}
