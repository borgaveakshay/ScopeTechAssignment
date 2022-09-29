package com.example.scopetechassignment.data.di

import com.example.scopetechassignment.data.repositoryImpl.GetUserDetailsRepositoryImpl
import com.example.scopetechassignment.data.repositoryImpl.StoreUserDetailsRepoImpl
import com.example.scopetechassignment.data.repositoryImpl.VehicleLocationRepoImpl
import com.example.scopetechassignment.domain.repositories.GetUserDetailsRepository
import com.example.scopetechassignment.domain.repositories.GetVehicleLocationRepository
import com.example.scopetechassignment.domain.repositories.StoreUserDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(getUserDetailsRepositoryImpl: GetUserDetailsRepositoryImpl): GetUserDetailsRepository

    @Binds
    abstract fun bindVehicleLocationRepository(getVehicleLocationRepoImpl: VehicleLocationRepoImpl): GetVehicleLocationRepository

    @Binds
    abstract fun bindStoreUserDetailsRepository(storeUserDetailsRepoImpl: StoreUserDetailsRepoImpl): StoreUserDetailsRepository
}