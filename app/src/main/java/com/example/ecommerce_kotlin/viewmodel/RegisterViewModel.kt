package com.example.ecommerce_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_kotlin.domain.model.RegisterRequest
import com.example.ecommerce_kotlin.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val nombreCompleto: String = "",
    val email: String = "",
    val password: String = "",
    val repetirPassword: String = "",
    val nombreError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val repetirPasswordError: String? = null,
    val errorMessage: String? = null,
    val showSuccessDialog: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onNombreCompletoChanged(value: String) {
        _uiState.value = _uiState.value.copy(nombreCompleto = value, nombreError = null)
    }

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(email = value, emailError = null)
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value, passwordError = null)
    }

    fun onRepetirPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(repetirPassword = value, repetirPasswordError = null)
    }

    fun registerUser() {
        val state = _uiState.value

        // Validaciones
        if (state.nombreCompleto.isBlank()) {
            _uiState.value = state.copy(nombreError = "El nombre completo es obligatorio")
            return
        }
        if (state.nombreCompleto.trim().split(" ").size < 2) {
            _uiState.value = state.copy(nombreError = "Ingresá nombre y apellido")
            return
        }

        if (state.email.isBlank()) {
            _uiState.value = state.copy(emailError = "El email es obligatorio")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _uiState.value = state.copy(emailError = "Formato de email inválido")
            return
        }

        if (state.password.isBlank()) {
            _uiState.value = state.copy(passwordError = "La contraseña es obligatoria")
            return
        }
        if (state.password.length < 6) {
            _uiState.value = state.copy(passwordError = "La contraseña debe tener al menos 6 caracteres")
            return
        }

        if (state.repetirPassword.isBlank()) {
            _uiState.value = state.copy(repetirPasswordError = "Repetir contraseña es obligatorio")
            return
        }
        if (state.repetirPassword != state.password) {
            _uiState.value = state.copy(repetirPasswordError = "Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            try {
                val result = repository.register(
                    RegisterRequest(
                        nombreCompleto = state.nombreCompleto.trim(),
                        email = state.email.trim(),
                        password = state.password
                    )
                )

                if (result.isSuccess) {
                    _uiState.value = state.copy(showSuccessDialog = true)
                } else {
                    _uiState.value = state.copy(errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido")
                }
            } catch (e: Exception) {
                _uiState.value = state.copy(errorMessage = e.message ?: "Error inesperado")
            }
        }
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun onSuccessDialogDismissed() {
        _uiState.value = _uiState.value.copy(showSuccessDialog = false)
    }

    fun isFormValid(): Boolean {
        val state = _uiState.value
        return state.nombreCompleto.trim().split(" ").size >= 2 &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(state.email.trim()).matches() &&
                state.password.length >= 6 &&
                state.password == state.repetirPassword
    }

}
