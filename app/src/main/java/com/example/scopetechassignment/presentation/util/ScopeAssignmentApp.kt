package com.example.scopetechassignment.presentation.util

import android.app.Application
import androidx.work.*
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

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.METERED)
            .build()

        WorkManager.getInstance(this).enqueue(
            PeriodicWorkRequestBuilder<DataSyncWorker>(1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        )
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .setWorkerFactory(MyWorkerFactory(storeUserDetailsUseCase))
        .build()
}