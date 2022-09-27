package com.example.scopetechassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.usecases.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetUserListViewModel @Inject constructor(private val getUserDetailsUseCase: GetUserDetailsUseCase) :
    ViewModel() {

    private val _userDetailsState = MutableStateFlow<Result<UserListResponse>>(Result.loading())
    val userDetailState: StateFlow<Result<UserListResponse>> = _userDetailsState

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            _userDetailsState.value = getUserDetailsUseCase()
        }
    }
}