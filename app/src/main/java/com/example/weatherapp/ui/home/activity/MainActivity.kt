package com.example.weatherapp.ui.home.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.airPollution.fragment.AirPollutionFragment
import com.example.weatherapp.ui.fiveforcast.fragment.FiveDayForecastFragment
import com.example.weatherapp.ui.home.fragment.HomeFragment
import com.example.weatherapp.ui.home.viewmodel.WeatherApiViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sdsdsd", "create activity: $this")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        loadFragment(HomeFragment())
        //use bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bar_home -> loadFragment(HomeFragment())
                R.id.bar_forecast_5_day -> loadFragment(FiveDayForecastFragment())
                R.id.bar_air_pollution -> loadFragment(AirPollutionFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("sdsdsd", "destroy activity: $this")
    }
}