package com.example.scopetechassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scopetechassignment.data.models.db.VehicleInformationEntity
import com.example.scopetechassignment.data.models.network.VehicleLocationResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.usecases.GetVehicleDetailsFromDbUseCase
import com.example.scopetechassignment.domain.usecases.GetVehicleLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetVehicleLocationViewModel @Inject constructor(
    private val vehicleLocationUseCase: GetVehicleLocationUseCase,
    private val vehicleDetailsFromDbUseCase: GetVehicleDetailsFromDbUseCase
) :
    ViewModel() {
    private val _vehicleLocationState =
        MutableStateFlow<Result<VehicleLocationResponse>>(Result.loading())
    val vehicleLocationResponseState: StateFlow<Result<VehicleLocationResponse>> =
        _vehicleLocationState
    private val _vehicleInfoState =
        MutableStateFlow<Result<List<VehicleInformationEntity>>>(Result.loading())
    val vehicleInfoState: StateFlow<Result<List<VehicleInformationEntity>>> = _vehicleInfoState

    fun getVehicleLocation(userId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _vehicleLocationState.emit(vehicleLocationUseCase(userId))
        }
    }

    fun getVehicleInfo(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _vehicleInfoState.emit(vehicleDetailsFromDbUseCase(userId))
        }
    }
}