package com.example.scopetechassignment.presentation.util

import android.app.Application
import androidx.work.*
import com.example.scopetechassignment.data.schedulers.DataSyncWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class ScopeAssignmentApp : Application() {

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
}