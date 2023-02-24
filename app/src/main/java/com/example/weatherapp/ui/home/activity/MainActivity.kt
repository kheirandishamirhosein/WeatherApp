package com.example.weatherapp.ui.home.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.R
import com.example.weatherapp.data.*
import com.example.weatherapp.data.model.WeatherModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.home.viewmodel.WeatherApiViewModel
import com.example.weatherapp.util.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherApiViewModel: WeatherApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.coLayout.visibility = View.GONE
        //binding.mdPcProgressLoading.visibility = View.VISIBLE
        getCurrentLocation()
        binding.textFieldEdit.setOnEditorActionListener { _, actionId, _ ->
            binding.mdPcProgressLoading.visibility = View.VISIBLE
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO: imp codes city edit text
                getCityWeather(binding.textFieldEdit.text.toString().trim())
                val view = this.currentFocus
                if (view != null) {
                    val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    binding.textFieldEdit.clearFocus()
                }
                true
            } else false
        }

    }

    private fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        weatherApiViewModel.fetchCurrentLocationWeather(latitude, longitude)
        weatherApiViewModel.currentWeatherStatus.observe(this) { setData(it) }
    }

    private fun getCityWeather(city: String) {
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        weatherApiViewModel.fetchCityWeather(city)
        weatherApiViewModel.cityWeatherStatus.observe(this) { setData(it) }

        //Toast.makeText(this,"City Not Found",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun setData(weatherModel: WeatherModel?) = with(binding) {
        mdTvDate.text = DateFormatter.currentDate
        mdTvTime.text = TimeFormatter.currentTime
        mdTvTemp.text = "" + KelvinToCelsius.kelvinToCelsius(weatherModel!!.main.temp) + ""
        mdTvCity.text = weatherModel.name
        mdTvWeatherType.text = weatherModel.weather[0].main
        mdTvFeelsLike.text = "Feels Like " + KelvinToCelsius.kelvinToCelsius(weatherModel.main.feels_like) + ""
        mdTvDayMaxTemp.text = "max " + KelvinToCelsius.kelvinToCelsius(weatherModel.main.temp_max) + ""
        mdTvDayMinTemp.text = "min " + KelvinToCelsius.kelvinToCelsius(weatherModel.main.temp_min) + ""
        mdTvNumSunrise.text = Timestamp.timestampToLocalDate(weatherModel.sys.sunrise.toLong())
        mdTvNumSunset.text = Timestamp.timestampToLocalDate(weatherModel.sys.sunset.toLong())
        mdTvNumPressure.text = weatherModel.main.pressure.toString() + " P"
        mdTvNumHumidity.text = weatherModel.main.humidity.toString() + "%"
        mdTvNumWindSpeed.text = weatherModel.wind.speed.toString() + " m/s"
        mdTvNumFahrenheit.text = "" + (CelsiusToFahrenheit.celsiusToFahrenheit(weatherModel.main.temp)) + " F"
        textFieldEdit.setText(weatherModel.name)
        updateImageWeather(weatherModel.weather[0].id)
    }

    private fun updateImageWeather(id: Int) = with(binding) {
        when (id) {
            in 200..232 -> {
                ivImages.setImageResource(R.drawable.ic_thunderstorm)
            }
            in 300..321 -> {
                ivImages.setImageResource(R.drawable.ic_drizzle_)
            }
            in 500..531 -> {
                ivImages.setImageResource(R.drawable.ic_rain)
            }
            in 600..622 -> {
                ivImages.setImageResource(R.drawable.ic_snow)
            }
            in 701..781 -> {
                ivImages.setImageResource(R.drawable.ic_atmosphere)
            }
            800 -> {
                ivImages.setImageResource(R.drawable.ic_img_clear)
            }
            in 801..804 -> {
                ivImages.setImageResource(R.drawable.ic_clouds)
            }
        }
        mdPcProgressLoading.visibility = View.GONE
        coLayout.visibility = View.VISIBLE
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
                            //binding.mdPcProgressLoading.visibility = View.VISIBLE
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