package com.example.scopetechassignment.presentation.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.scopetechassignment.data.models.network.VehicleLocationModel
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.presentation.util.collectLatestLifecycleFlow
import com.example.scopetechassignment.presentation.viewmodels.GetVehicleLocationViewModel
import com.google.android.gms.location.*
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
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.extras?.getInt("userId")
        getVehicleLocation(userId.toString())
        checkForLocationPermission()
        checkForCurrentLocation()
    }

    private fun checkForCurrentLocation() {
        fusedLocationClient?.let {
            val locationRequest = LocationRequest.create()
            locationRequest.interval = 10000
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                checkForLocationPermission()
                return
            }
            it.requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    setContent {
                        locationResult.lastLocation?.let { location ->
                            GetVehicleLocationAndMap(lastKnownLocation = location)
                        }
                    }
                }
            }, Looper.getMainLooper())
        }
    }

    @Composable
    private fun GetVehicleLocationAndMap(lastKnownLocation: Location) {
        val vehicleLocationState = remember {
            mutableStateOf<List<VehicleLocationModel>>(
                emptyList()
            )
        }
        LoadGoogleMap(
            vehicleList = vehicleLocationState.value,
            lastKnownLocation = lastKnownLocation
        )
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

    private fun getVehicleLocation(userId: String?) = viewModel.getVehicleLocation(userId)

    private fun checkForLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    checkForCurrentLocation()

                } else {
                    Toast.makeText(
                        this,
                        "We need location permission to locate you correctly.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }
}

@Composable
fun LoadGoogleMap(
    modifier: Modifier = Modifier,
    vehicleList: List<VehicleLocationModel>,
    lastKnownLocation: Location
) {
    if (vehicleList.isNotEmpty()) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), 15f
            )
        }

        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {

            Marker(
                state = MarkerState(
                    position = LatLng(
                        lastKnownLocation.latitude,
                        lastKnownLocation.longitude
                    )
                ),
                title = "Its Me",
                snippet = "My Current Location"
            )

            vehicleList.forEach {
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            it.lat,
                            it.lon
                        )
                    ),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                )
            }
        }
    }
}
