package com.example.scopetechassignment.data.schedulers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.scopetechassignment.domain.usecases.StoreUserDetailsUseCase

class DataSyncWorker(
    context: Context, parameters: WorkerParameters,
    private val storeUserDetailsUseCase: StoreUserDetailsUseCase
) : CoroutineWorker(context, parameters) {
    override suspend fun doWork(): Result = try {
        storeUserDetailsUseCase()
        Result.success()
    } catch (e: Exception) {
        Result.retry()
    }
}
