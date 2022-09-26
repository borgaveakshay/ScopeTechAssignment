package com.example.scopetechassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.scopetechassignment.domain.usecases.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetUserListViewModel @Inject constructor(private val getUserDetailsUseCase: GetUserDetailsUseCase) :
    ViewModel() {


}