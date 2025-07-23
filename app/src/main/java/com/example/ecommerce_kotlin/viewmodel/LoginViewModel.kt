package com.example.ecommerce_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_kotlin.domain.repository.AuthRepository
import com.example.ecommerce_kotlin.data.datastore.UserPreferences
import com.example.ecommerce_kotlin.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successLogin: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(email = value, emailError = false)
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value, passwordError = false)
    }

    fun onLoginClicked() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(
                emailError = email.isBlank(),
                passwordError = password.isBlank()
            )
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val result = repository.login(email, password)
                if (result.isSuccess) {
                    val userResponse = result.getOrNull()


                    val token = UUID.randomUUID().toString()
                    userPreferences.saveToken(token)


                    userResponse?.let {
                        val user = User(
                            id = it.id,
                            name = it.name,
                            email = it.email,
                            password = "",
                            avatar = "",
                            createdAt = "it.createdAt"
                        )
                        userPreferences.saveUser(user)
                    }

                    _uiState.value = _uiState.value.copy(
                        successLogin = true,
                        isLoading = false,
                        errorMessage = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.exceptionOrNull()?.message ?: "Error de autenticación",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Error inesperado",
                    isLoading = false
                )
            }
        }
    }
}
