package com.example.ecommerce_kotlin.di

import com.example.ecommerce_kotlin.data.repository.AuthRepositoryImpl
import com.example.ecommerce_kotlin.domain.repository.AuthRepository
import com.example.ecommerce_kotlin.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }
}
