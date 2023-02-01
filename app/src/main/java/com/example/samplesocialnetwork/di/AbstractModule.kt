package com.example.samplesocialnetwork.di

import com.example.samplesocialnetwork.datasource.local.LocalDataSource
import com.example.samplesocialnetwork.datasource.local.LocalDataSourceImpl
import com.example.samplesocialnetwork.repository.Repository
import com.example.samplesocialnetwork.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}