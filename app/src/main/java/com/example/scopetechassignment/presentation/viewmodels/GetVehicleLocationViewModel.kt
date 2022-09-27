package com.example.scopetechassignment.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scopetechassignment.data.models.network.VehicleLocationResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.usecases.GetVehicleLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetVehicleLocationViewModel @Inject constructor(private val vehicleLocationUseCase: GetVehicleLocationUseCase) :
    ViewModel() {
    private val _vehicleLocationState =
        mutableStateOf<Result<VehicleLocationResponse>>(Result.loading())
    val vehicleLocationResponseState = _vehicleLocationState

    fun getVehicleLocation(userId: String) {
        viewModelScope.launch {
            _vehicleLocationState.value = vehicleLocationUseCase(userId)
        }
    }
}