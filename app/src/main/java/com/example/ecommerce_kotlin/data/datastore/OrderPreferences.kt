package com.example.ecommerce_kotlin.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ecommerce_kotlin.domain.model.Order
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class OrderPreferences(private val context: Context) {

    private val gson = Gson()

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "order_data")
        private val ORDER_KEY = stringPreferencesKey("orders")
    }

    fun getOrders(): Flow<List<Order>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[ORDER_KEY]
            if (json != null) {
                val type = object : TypeToken<List<Order>>() {}.type
                gson.fromJson(json, type)
            } else {
                emptyList()
            }
        }
    }

    suspend fun saveOrder(order: Order) {
        val currentOrders = getOrders().firstOrNull()?.toMutableList() ?: mutableListOf()
        currentOrders.add(order)

        val json = gson.toJson(currentOrders)
        context.dataStore.edit { prefs ->
            prefs[ORDER_KEY] = json
        }
    }

    suspend fun clearOrders() {
        context.dataStore.edit { prefs ->
            prefs.remove(ORDER_KEY)
        }
    }
}
