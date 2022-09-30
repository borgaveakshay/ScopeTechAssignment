package com.example.scopetechassignment.data.schedulers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.scopetechassignment.domain.usecases.StoreUserDetailsUseCase

class MyWorkerFactory(private val storeUserDetailsUseCase: StoreUserDetailsUseCase) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = DataSyncWorker(
        context = appContext,
        parameters = workerParameters,
        storeUserDetailsUseCase = storeUserDetailsUseCase
    )
}