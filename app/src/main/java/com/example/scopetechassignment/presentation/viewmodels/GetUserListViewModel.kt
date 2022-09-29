package com.example.scopetechassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scopetechassignment.data.models.db.UserEntity
import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.domain.usecases.GetUserDetailsUseCase
import com.example.scopetechassignment.domain.usecases.GetUserInfoFromDbUseCase
import com.example.scopetechassignment.domain.usecases.StoreUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserListViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val storeUserDetailsUseCase: StoreUserDetailsUseCase,
    private val getUserInfoFromDbUseCase: GetUserInfoFromDbUseCase
) :
    ViewModel() {

    private val _userDetailsState = MutableStateFlow<Result<UserListResponse>>(Result.loading())
    val userDetailState: StateFlow<Result<UserListResponse>> = _userDetailsState
    private val _userDetailsFromDbState =
        MutableStateFlow<Result<List<UserEntity>>>(Result.loading())
    val userDetailsFromDbState: StateFlow<Result<List<UserEntity>>> = _userDetailsFromDbState

    init {
        getUserDetailsFromDb()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            _userDetailsState.emit(getUserDetailsUseCase())
        }
    }

    private fun getUserDetailsFromDb() {
        viewModelScope.launch {
            val result = getUserInfoFromDbUseCase()
            when (result.status) {
                Status.ERROR -> {
                    storeUserDetailsUseCase()
                }
                Status.SUCCESS -> _userDetailsFromDbState.emit(result)
                else -> {
                    // Do nothing
                }
            }
        }
    }
}