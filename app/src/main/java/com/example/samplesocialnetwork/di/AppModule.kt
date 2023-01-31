package com.example.samplesocialnetwork.di

import android.app.Application
import androidx.room.Room
import com.example.samplesocialnetwork.datasource.local.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MyDatabase = Room.databaseBuilder(
        application, MyDatabase::class.java, "my_database").build()
}