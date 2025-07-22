package com.example.ecommerce_kotlin.di

import com.example.ecommerce_kotlin.data.remote.ApiService
import com.example.ecommerce_kotlin.data.remote.RetrofitInstance
import com.example.ecommerce_kotlin.data.repository.AuthRepositoryImpl
import com.example.ecommerce_kotlin.domain.repository.AuthRepository
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
    fun provideApiService(): ApiService = RetrofitInstance.api

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository =
        AuthRepositoryImpl(apiService)
}
