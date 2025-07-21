package com.example.ecommerce_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isLoading: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(email = email, emailError = false)
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password, passwordError = false)
        }
    }

    fun onLoginClicked() {
        val current = _uiState.value
        val emailBlank = current.email.isBlank()
        val passwordBlank = current.password.isBlank()

        if (emailBlank || passwordBlank) {
            _uiState.update {
                it.copy(
                    emailError = emailBlank,
                    passwordError = passwordBlank
                )
            }
            return
        }

        // Simular carga (llamado a API)
        _uiState.update { it.copy(isLoading = true) }

        //  llamada a login con Retrofit + corutinas
    }
}
