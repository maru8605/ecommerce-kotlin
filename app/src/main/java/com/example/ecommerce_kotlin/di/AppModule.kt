package com.example.ecommerce_kotlin.di

import android.content.Context
import com.example.ecommerce_kotlin.data.datastore.UserPreferences
import com.example.ecommerce_kotlin.data.remote.ApiService
import com.example.ecommerce_kotlin.data.remote.RetrofitInstance
import com.example.ecommerce_kotlin.data.repository.AuthRepositoryImpl
import com.example.ecommerce_kotlin.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

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

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}
