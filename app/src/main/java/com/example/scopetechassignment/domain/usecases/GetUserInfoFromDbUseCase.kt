package com.example.scopetechassignment.domain.usecases

import android.database.sqlite.SQLiteException
import com.example.scopetechassignment.data.dao.UserDao
import com.example.scopetechassignment.data.models.db.UserEntity
import com.example.scopetechassignment.domain.Result
import javax.inject.Inject

class GetUserInfoFromDbUseCase @Inject constructor(private val userDao: UserDao) :
    BaseUseCase<Unit, Result<List<UserEntity>>>() {
    override suspend fun execute(request: Unit?): Result<List<UserEntity>> = try {
        Result.success(userDao.retrieveAll())
    } catch (e: SQLiteException) {
        Result.error(e)
    }
}