package com.example.scopetechassignment.domain

enum class Status {
    LOADING, ERROR, SUCCESS
}

@Suppress("DataClassPrivateConstructor")
data class Result<RES> private constructor(
    val status: Status,
    val data: RES?,
    val errorMessage: String?
) {
    companion object {
        fun <RES> success(result: RES): Result<RES> = Result(Status.SUCCESS, result, null)
        fun <RES> error(exception: Exception): Result<RES> =
            Result(Status.ERROR, null, exception.message)

        fun <RES> error(exceptionMessage: String): Result<RES> =
            Result(Status.ERROR, null, exceptionMessage)

        fun <RES> loading(): Result<RES> = Result(Status.LOADING, null, null)
    }
}
