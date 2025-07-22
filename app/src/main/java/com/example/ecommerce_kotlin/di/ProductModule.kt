package com.example.ecommerce_kotlin.di

import com.example.ecommerce_kotlin.data.remote.ApiService
import com.example.ecommerce_kotlin.data.repository.ProductRepositoryImpl
import com.example.ecommerce_kotlin.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }
}
