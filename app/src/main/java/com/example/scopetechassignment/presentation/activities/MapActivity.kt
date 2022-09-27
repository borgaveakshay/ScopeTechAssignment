package com.example.scopetechassignment.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.scopetechassignment.data.models.network.VehicleLocationModel
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.presentation.util.collectLatestLifecycleFlow
import com.example.scopetechassignment.presentation.viewmodels.GetVehicleLocationViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.AndroidEntryPoint

private const val LOCATION_REQUEST_CODE = 1

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private val viewModel by viewModels<GetVehicleLocationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.extras?.getString("userId")
        checkForLocationPermission()
        getVehicleLocation(userId)
        setContent {
            val vehicleLocationState = remember {
                mutableStateOf<List<VehicleLocationModel>>(
                    emptyList()
                )
            }
            LoadGoogleMap(vehicleList = vehicleLocationState.value)
            collectLatestLifecycleFlow(viewModel.vehicleLocationResponseState) {
                when (it.status) {
                    Status.LOADING -> {
                        // Do nothing
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Error: ${it.errorMessage}", Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS -> {
                        it.data?.let { response ->
                            vehicleLocationState.value = response.vehicleGeoData
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LoadGoogleMap(modifier: Modifier = Modifier, vehicleList: List<VehicleLocationModel>) {
        if (vehicleList.isNotEmpty()) {
            val firstVehicle = LatLng(vehicleList[0].lat, vehicleList[0].lon)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(firstVehicle, 10f)
            }
            GoogleMap(
                modifier = modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = firstVehicle),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                )
                if (vehicleList.size > 1) {
                    for (i in 1..vehicleList.size) {
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    vehicleList[i].lat,
                                    vehicleList[i].lon
                                )
                            ),
                            title = "Singapore",
                            snippet = "Marker in Singapore"
                        )
                    }
                }
            }
        }
    }

    private fun getVehicleLocation(userId: String?) = viewModel.getVehicleLocation(userId)


    private fun checkForLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(
                    this,
                    "Application requires location permission for better map performance",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
                )
            }
        }
    }
}