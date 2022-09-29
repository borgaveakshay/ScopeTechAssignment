package com.example.scopetechassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scopetechassignment.data.models.db.UserEntity
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.domain.usecases.GetUserInfoFromDbUseCase
import com.example.scopetechassignment.domain.usecases.StoreUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserListViewModel @Inject constructor(
    private val storeUserDetailsUseCase: StoreUserDetailsUseCase,
    private val getUserInfoFromDbUseCase: GetUserInfoFromDbUseCase
) : ViewModel() {

    private val _userDetailsFromDbState =
        MutableStateFlow<Result<List<UserEntity>>>(Result.loading())
    val userDetailsFromDbState: StateFlow<Result<List<UserEntity>>> = _userDetailsFromDbState

    init {
        getUserDetailsFromDb()
    }

    private fun getUserDetailsFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserInfoFromDbUseCase()
            when (result.status) {
                Status.ERROR -> {
                    storeUserDetailsUseCase()
                }
                Status.SUCCESS -> {
                    result.data?.let {
                        it.collect { userList ->
                            if (userList.isEmpty()) storeUserDetailsUseCase() else _userDetailsFromDbState.emit(
                                Result.success(userList)
                            )
                        }
                    }
                }
                else -> {
                    // Do nothing
                }
            }
        }
    }
}