package com.example.ecommerce_kotlin.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_kotlin.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()


    LaunchedEffect(state.successLogin) {
        if (state.successLogin) {
            navController.navigate("catalog") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

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
            value = state.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Email") },
            placeholder = { Text("usuario@email.com") },
            leadingIcon = { Icon(Icons.Default.MailOutline, contentDescription = "Email") },
            isError = state.emailError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.emailError) {
            Text("El email es obligatorio", color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Contraseña") },
            placeholder = { Text("********") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = state.passwordError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.passwordError) {
            Text("La contraseña es obligatoria", color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.onLoginClicked() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }
        }

        state.errorMessage?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = {
            navController.navigate("registro") {
                popUpTo("login") { inclusive = false }
            }
        }) {
            Text("¿No tenés cuenta? Registrate")
        }
    }
}
