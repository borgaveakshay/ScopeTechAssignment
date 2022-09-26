package com.example.scopetechassignment.domain.usecases

abstract class BaseUseCase<REQ, RES> {

    abstract suspend fun execute(request: REQ? = null): RES

    suspend operator fun invoke(request: REQ? = null): RES = execute(request)
}