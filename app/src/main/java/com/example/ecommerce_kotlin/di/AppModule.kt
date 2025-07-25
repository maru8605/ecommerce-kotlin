package com.example.ecommerce_kotlin.di

import android.app.Application
import com.example.ecommerce_kotlin.data.datastore.OrderPreferences
import com.example.ecommerce_kotlin.data.local.dao.CartDao
import com.example.ecommerce_kotlin.data.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepository(cartDao)
    }

    @Provides
    @Singleton
    fun provideOrderPreferences(app: Application): OrderPreferences {
        return OrderPreferences(app)
    }
}
