package com.example.scopetechassignment.data.di

import com.example.scopetechassignment.BuildConfig
import com.example.scopetechassignment.data.api.UserDetailsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun userDetailsAPI(retrofit: Retrofit): UserDetailsAPI =
        retrofit.create(UserDetailsAPI::class.java)
}