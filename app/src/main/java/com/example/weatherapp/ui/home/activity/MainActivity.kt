package com.example.weatherapp.ui.home.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherApiViewModel: WeatherApiViewModel by viewModels()
    private val homeFragment = HomeFragment()
    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()

        //use nav host fragment
        /*       val navHostFragment = supportFragmentManager
                   .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
               navController = navHostFragment.navController
               NavigationUI.setupActionBarWithNavController(this, navController)
       */

        loadFragment(homeFragment)
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

    //fetch Current Location Weather latitude , longitude
    private fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        //TODO: visibility is gone
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        weatherApiViewModel.fetchCurrentLocationWeather(latitude, longitude)
        weatherApiViewModel.currentWeatherStatus.observe(this) { homeFragment.setDataView(it) }
    }


    /***permissions***/
    /*** imps all permissions in fun getCurrentLocation ***/
    private fun getCurrentLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                // latitude and longitude
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions()
                    return
                }
                fusedLocationClient.lastLocation
                    .addOnCompleteListener(this) { task ->
                        // Got last known location. In some rare situations this can be null.
                        val location: Location? = task.result
                        if (location == null) {
                            Toast.makeText(this, "Null Receive", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            //TODO: imp codes and fetch location weathers
                            fetchCurrentLocationWeather(
                                location.latitude.toString(),
                                location.longitude.toString()
                            )
                        }
                    }
            } else {
                //setting
                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            //request permission
            requestPermissions()
        }
    }

    //check permission
    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    //request Permission
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            777
        )
    }

    //override request Permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 777) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //is Location Enabled(GPS_PROVIDER,NETWORK_PROVIDER)
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


}