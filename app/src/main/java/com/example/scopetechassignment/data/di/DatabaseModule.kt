package com.example.scopetechassignment.data.di

import android.content.Context
import androidx.room.Room
import com.example.scopetechassignment.data.models.db.ScopeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "scope_tech_assignment_db"

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): ScopeDatabase =
        Room.databaseBuilder(context, ScopeDatabase::class.java, DATABASE_NAME)
            .build()

}