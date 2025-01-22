package com.example.data.di


import com.example.data.repository.RepositoryImpl
import com.example.data.sources.LocalDataSource
import com.example.data.sources.RemoteDataSource
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository {
        return RepositoryImpl(remoteDataSource, localDataSource)
    }
}