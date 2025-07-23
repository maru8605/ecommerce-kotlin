package com.example.ecommerce_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_kotlin.data.datastore.UserPreferences
import com.example.ecommerce_kotlin.domain.model.User
import com.example.ecommerce_kotlin.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val userData = userPreferences.getUser().first()
            _user.value = userData
        }
    }

    fun updateAvatarAndSync(url: String) {
        val currentUser = _user.value ?: return

        val updatedUser = currentUser.copy(avatar = url)
        _user.value = updatedUser

        viewModelScope.launch {
            userPreferences.saveUser(updatedUser)
            if (currentUser.id.isNotBlank()) {
                userRepository.updateUserAvatar(currentUser.id, url)
            }
        }
    }

    fun updateProfileImage(url: String) {
        _user.value?.let { currentUser ->
            val updatedUser = currentUser.copy(avatar = url)
            _user.value = updatedUser

            viewModelScope.launch {

                userPreferences.saveUser(updatedUser)


                if (currentUser.id.isNotBlank()) {
                    try {
                        userRepository.updateUserAvatar(currentUser.id, url)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


}

