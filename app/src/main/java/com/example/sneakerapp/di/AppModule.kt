package com.example.sneakerapp.di

import android.content.Context
import androidx.room.Room
import com.example.sneakerapp.data.datasource.LocalSneakerDataSource
import com.example.sneakerapp.data.datasource.SneakerDataSource
import com.example.sneakerapp.data.local.SneakerDatabase
import com.example.sneakerapp.data.local.SneakerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): SneakerDatabase = Room.databaseBuilder(
        context,
        SneakerDatabase::class.java,
        "sneaker_database"
    ).build()

    @Provides
    @Singleton
    fun provideSneakerDao(database: SneakerDatabase): SneakerDao =
        database.sneakerDao()

    @Provides
    @Singleton
    fun provideSneakerDataSource(): SneakerDataSource = LocalSneakerDataSource()
} 