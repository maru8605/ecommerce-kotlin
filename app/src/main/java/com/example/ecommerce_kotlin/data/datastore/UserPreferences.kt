package com.example.ecommerce_kotlin.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences @Inject constructor(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val TOKEN_EXPIRATION_KEY = longPreferencesKey("token_expiration")
    }

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
}
