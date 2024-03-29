package com.example.weatherapp.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainWeatherBinding
import com.example.weatherapp.ui.airPollution.fragment.AirPollutionFragment
import com.example.weatherapp.ui.calendar.fragment.CalendarFragment
import com.example.weatherapp.ui.fiveforcast.fragment.FiveDayForecastFragment
import com.example.weatherapp.ui.home.fragment.HomeFragment
import com.example.weatherapp.util.LocationPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivityWeather : AppCompatActivity() {

    private lateinit var binding: ActivityMainWeatherBinding
    @Inject
    lateinit var locationPermission: LocationPermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (locationPermission.checkLocationPermission()) {
            Log.i("Location Permission", locationPermission.checkLocationPermission().toString())
        } else {
            locationPermission.requestLocationPermission()
        }

        supportActionBar?.hide()

        loadFragment(HomeFragment())
        //use bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bar_home -> loadFragment(HomeFragment())
                R.id.bar_forecast_5_day -> loadFragment(FiveDayForecastFragment())
                R.id.bar_air_pollution -> loadFragment(AirPollutionFragment())
                R.id.bar_calendar -> loadFragment(CalendarFragment())
            }
            true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Are you sure to exit?")
            .setPositiveButton("Yes") { _, _ ->
                finishAffinity()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    //location permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LocationPermission.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


}