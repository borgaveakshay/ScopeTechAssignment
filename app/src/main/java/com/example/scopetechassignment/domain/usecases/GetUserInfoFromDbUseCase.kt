package com.example.scopetechassignment.domain.usecases

import android.database.sqlite.SQLiteException
import com.example.scopetechassignment.data.dao.UserDao
import com.example.scopetechassignment.data.models.db.UserEntity
import com.example.scopetechassignment.domain.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoFromDbUseCase @Inject constructor(private val userDao: UserDao) :
    BaseUseCase<Unit, Result<Flow<List<UserEntity>>>>() {
    override suspend fun execute(request: Unit?): Result<Flow<List<UserEntity>>> = try {
        Result.success(userDao.retrieveAllFromFlow())
    } catch (e: SQLiteException) {
        Result.error(e)
    }
}