package com.example.ecommerce_kotlin.ui.register

import androidx.compose.foundation.layout.*
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
import com.example.ecommerce_kotlin.viewmodel.RegisterViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var repeatPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = state.nombreCompleto,
            onValueChange = { viewModel.onNombreCompletoChanged(it) },
            label = { Text("Nombre completo") },
            isError = state.nombreError != null,
            modifier = Modifier.fillMaxWidth()
        )
        state.nombreError?.let { Text(it, color = Color.Red) }

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Email") },
            isError = state.emailError != null,
            modifier = Modifier.fillMaxWidth()
        )
        state.emailError?.let { Text(it, color = Color.Red) }

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Contraseña") },
            isError = state.passwordError != null,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    val desc = if (passwordVisible) "Ocultar" else "Mostrar"
                    Icon(imageVector = icon, contentDescription = desc)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        state.passwordError?.let { Text(it, color = Color.Red) }

        OutlinedTextField(
            value = state.repetirPassword,
            onValueChange = { viewModel.onRepetirPasswordChanged(it) },
            label = { Text("Repetir contraseña") },
            isError = state.repetirPasswordError != null,
            visualTransformation = if (repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { repeatPasswordVisible = !repeatPasswordVisible }) {
                    val icon = if (repeatPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    val desc = if (repeatPasswordVisible) "Ocultar" else "Mostrar"
                    Icon(imageVector = icon, contentDescription = desc)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        state.repetirPasswordError?.let { Text(it, color = Color.Red) }

        Button(
            onClick = { viewModel.registerUser() },
            enabled = viewModel.isFormValid(),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Registrarse")
        }


        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tenés cuenta? Iniciar sesión", color = Color.Red)
        }
    }

    if (state.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onSuccessDialogDismissed()
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }) {
                    Text("Aceptar")
                }
            },
            title = { Text("Registro exitoso") },
            text = { Text("Tu cuenta fue creada correctamente. Ahora podés iniciar sesión.") }
        )
    }

    if (state.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissError() },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissError() }) {
                    Text("Aceptar")
                }
            },
            title = { Text("Error") },
            text = { Text(state.errorMessage ?: "") }
        )
    }
}
