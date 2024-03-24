package com.example.weatherapp.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationPermission(private val activity: Activity) {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 777
    }

    //Location Permission
    fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    //is Location Enabled(GPS_PROVIDER,NETWORK_PROVIDER)
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as
                LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // function get current location
    fun getCurrentLocation(onLocationReceived: (Location?) -> Unit) {
        if (checkLocationPermission()) {
            if (isLocationEnabled()) {
                val fusedLocationClient: FusedLocationProviderClient = LocationServices
                    .getFusedLocationProviderClient(activity)
                fusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                    val location: Location? = task.result
                    onLocationReceived(location)
                }
            }
        }
    }


}