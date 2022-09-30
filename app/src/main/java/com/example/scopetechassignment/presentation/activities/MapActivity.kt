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
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.scopetechassignment.R
import com.example.scopetechassignment.data.models.db.VehicleInformationEntity
import com.example.scopetechassignment.data.models.network.VehicleLocationModel
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.presentation.util.bitmapDescriptor
import com.example.scopetechassignment.presentation.util.collectLifecycleFlow
import com.example.scopetechassignment.presentation.viewmodels.GetVehicleLocationViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapActivity : LocationActivity() {
    private val viewModel by viewModels<GetVehicleLocationViewModel>()
    private var userId: Int? = null
    private val firstCallTime =
        kotlin.math.ceil(System.currentTimeMillis() / 60_000.0).toLong() * 60_000
    private lateinit var getLocationJob: Job
    private var vehicleList: List<VehicleLocationModel>? = null
    private var vehicleInfoList: List<VehicleInformationEntity>? = null
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.extras?.getInt("userId")
        collectVehicleInformation()
        getVehicleLocation(userId.toString())
        checkForCurrentLocation()
        getVehicleDetailsEveryMinute()
        collectLifecycleFlow(viewModel.vehicleLocationResponseState) {
            when (it.status) {
                Status.LOADING -> {
                    // Do nothing
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Error: ${it.errorMessage}", Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    vehicleList = it.data?.vehicleGeoData
                    setMapView()
                }
            }
        }
        setMapView()
    }

    private fun collectVehicleInformation() {
        userId?.let { viewModel.getVehicleInfo(it) }
        collectLifecycleFlow(viewModel.vehicleInfoState) {
            when (it.status) {
                Status.LOADING -> {
                    // Do nothing
                }
                Status.ERROR -> {
                    Toast.makeText(this, "Error: ${it.errorMessage}", Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    vehicleInfoList = it.data
                }
            }
        }
    }

    private fun setMapView() {
        setContent {
            GetVehicleLocationAndMap(
                lastKnownLocation = lastKnownLocation,
                vehicleList = vehicleList,
                vehicleInformationList = vehicleInfoList
            )
        }
    }

    private fun getVehicleDetailsEveryMinute() {
        getLocationJob = lifecycleScope.launch {
            // suspend till first minute comes after some seconds
            delay(firstCallTime - System.currentTimeMillis())
            while (true) {
                launch {
                    getVehicleLocation(userId.toString())
                }
                delay(60_000)  // 1 minute delay (suspending)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        getLocationJob.cancel()
    }

    @SuppressLint("MissingPermission")
    private fun checkForCurrentLocation() {
        fusedLocationClient?.let {
            val locationRequest = LocationRequest.create()
            if (isLocationPermissionGiven()) {
                it.requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        locationResult.lastLocation?.let { location ->
                            lastKnownLocation = location
                        }
                    }
                }, Looper.getMainLooper())
            }
        }
    }

    @Composable
    private fun GetVehicleLocationAndMap(
        lastKnownLocation: Location?,
        vehicleList: List<VehicleLocationModel>?,
        vehicleInformationList: List<VehicleInformationEntity>?
    ) {
        lastKnownLocation?.let { location ->
            vehicleList?.let { vehicleList ->
                LoadGoogleMap(
                    vehicleList = vehicleList,
                    lastKnownLocation = location,
                    context = this,
                    vehicleInformationList = vehicleInformationList
                )
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
    vehicleInformationList: List<VehicleInformationEntity>?
) {
    if (vehicleList.isNotEmpty()) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), 11.5f
            )
        }


        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true)
        ) {

            MapMarker(
                context = context, position = LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), title = "Its Mee", snippet = "My Location", iconResourceId = R.drawable.location
            )

            vehicleInformationList?.forEach { vehicleInfo ->
                vehicleList.forEach { location ->
                    MapMarker(
                        context = context,
                        position = LatLng(
                            location.lat,
                            location.lon
                        ),
                        title = "${vehicleInfo.make} ${vehicleInfo.model}",
                        snippet = "Color: ${vehicleInfo.color}\nYear: ${vehicleInfo.year}",
                        iconResourceId = R.drawable.gps_navigation
                    )
                }
            }

        }
    }
}
