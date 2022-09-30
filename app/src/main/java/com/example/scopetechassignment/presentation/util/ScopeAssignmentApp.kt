package com.example.scopetechassignment.presentation.util

import android.app.Application
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.scopetechassignment.data.schedulers.DataSyncWorker
import com.example.scopetechassignment.data.schedulers.MyWorkerFactory
import com.example.scopetechassignment.domain.usecases.StoreUserDetailsUseCase
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ScopeAssignmentApp : Application(), Configuration.Provider {

    @Inject
    lateinit var storeUserDetailsUseCase: StoreUserDetailsUseCase

    override fun onCreate() {
        super.onCreate()
        WorkManager.getInstance(this).enqueue(
            PeriodicWorkRequestBuilder<DataSyncWorker>(
                12, TimeUnit.HOURS,
                1, TimeUnit.HOURS
            ).build()
        )
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .setWorkerFactory(MyWorkerFactory(storeUserDetailsUseCase))
        .build()
}