package com.example.ecommerce_kotlin.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.ecommerce_kotlin.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences @Inject constructor(private val context: Context) {

    suspend fun saveToken(token: String) {
        val expiration = System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000 // 30 días
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[TOKEN_EXPIRATION_KEY] = expiration
        }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[TOKEN_KEY]
        }
    }

    fun isTokenValid(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            val expiration = prefs[TOKEN_EXPIRATION_KEY] ?: 0L
            System.currentTimeMillis() < expiration
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(TOKEN_EXPIRATION_KEY)
        }
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = user.name
            preferences[USER_EMAIL_KEY] = user.email
            preferences[USER_AVATAR_KEY] = user.avatar
        }
    }

    fun getUser(): Flow<User> = context.dataStore.data.map { preferences ->
        User(
            id = "",
            name = preferences[USER_NAME_KEY] ?: "",
            email = preferences[USER_EMAIL_KEY] ?: "",
            password = "",
            avatar = preferences[USER_AVATAR_KEY] ?: "",
            createdAt = ""
        )
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val TOKEN_EXPIRATION_KEY = longPreferencesKey("token_expiration")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_AVATAR_KEY = stringPreferencesKey("user_avatar")
    }
}
