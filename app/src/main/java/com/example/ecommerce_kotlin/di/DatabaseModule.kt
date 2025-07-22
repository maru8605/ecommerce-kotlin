package com.example.ecommerce_kotlin.di

import android.content.Context
import androidx.room.Room
import com.example.ecommerce_kotlin.data.local.dao.CartDao
import com.example.ecommerce_kotlin.data.local.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCartDatabase(@ApplicationContext appContext: Context): CartDatabase {
        return Room.databaseBuilder(
            appContext,
            CartDatabase::class.java,
            "cart_database"
        ).build()
    }

    @Provides
    fun provideCartDao(db: CartDatabase): CartDao = db.cartDao()
}
