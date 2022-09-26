package com.example.scopetechassignment.data.di

import com.example.scopetechassignment.data.repositoryImpl.GetUserDetailsRepositoryImpl
import com.example.scopetechassignment.domain.repositories.GetUserDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(getUserDetailsRepositoryImpl: GetUserDetailsRepositoryImpl): GetUserDetailsRepository
}