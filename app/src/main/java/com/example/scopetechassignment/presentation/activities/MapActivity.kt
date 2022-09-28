package com.example.scopetechassignment.presentation.activities

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.scopetechassignment.R
import com.example.scopetechassignment.data.models.db.VehicleInformation
import com.example.scopetechassignment.data.models.network.VehicleLocationModel
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.presentation.util.bitmapDescriptor
import com.example.scopetechassignment.presentation.util.collectLatestLifecycleFlow
import com.example.scopetechassignment.presentation.viewmodels.GetVehicleLocationViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapActivity : LocationActivity() {
    private val viewModel by viewModels<GetVehicleLocationViewModel>()
    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.extras?.getInt("userId")
        getVehicleLocation(userId.toString())
        checkForCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    private fun checkForCurrentLocation() {
        fusedLocationClient?.let {
            val locationRequest = LocationRequest.create()
            locationRequest.interval = 10000
            if (isLocationPermissionGiven()) {
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
            lastKnownLocation = lastKnownLocation,
            context = this
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

}

@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptor(
        context, iconResourceId
    )
    Marker(
        state = MarkerState(position),
        title = title,
        snippet = snippet,
        icon = icon,
    )
}

@Composable
fun LoadGoogleMap(
    modifier: Modifier = Modifier,
    vehicleList: List<VehicleLocationModel>,
    lastKnownLocation: Location,
    context: Context,
    vehicleInformation: VehicleInformation = VehicleInformation(
        color = "Red",
        photo = "",
        make = "VW",
        model = "POLO",
        vehicleId = 1,
        vin = "",
        year = "2012"
    )
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
            cameraPositionState = cameraPositionState,
            properties =  MapProperties(isMyLocationEnabled = true)
        ) {

            MapMarker(
                context = context, position = LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), title = "Its Mee", snippet = "My Location", iconResourceId = R.drawable.location
            )

            vehicleList.forEach {
                MapMarker(
                    context = context,
                    position = LatLng(
                        it.lat,
                        it.lon
                    ),
                    title = "${vehicleInformation.make} ${vehicleInformation.model}",
                    snippet = "Color: ${vehicleInformation.color}\nYear: ${vehicleInformation.year}",
                    iconResourceId = R.drawable.gps_navigation
                )
            }
        }
    }
}
