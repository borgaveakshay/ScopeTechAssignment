package com.example.scopetechassignment.data.di

import com.example.scopetechassignment.domain.repositories.GetUserDetailsRepository
import com.example.scopetechassignment.domain.usecases.GetUserDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun getUserDetailsUseCase(userDetailsRepository: GetUserDetailsRepository) =
        GetUserDetailsUseCase(userDetailsRepository)
}