package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.weatherapp.api.Api
import com.example.weatherapp.data.*
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.progressindicator.CircularProgressIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.coLayout.visibility = View.GONE
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        getCurrentLocation()

        binding.textFieldEdit.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO: imp codes city edit text
                binding.mdPcProgressLoading.visibility = View.VISIBLE
                binding.coLayout.visibility = View.VISIBLE
                //(binding.textFieldEdit.text.toString())
                //(binding.mdTvCity.text.toString())
                getCityWeather(binding.textFieldEdit.text.toString())
                getCityWeather(binding.mdTvCity.text.toString())
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

    private fun getCityWeather(city: String) {
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        //GetApi.retrofitService.getCityWeatherData
        Api.getApiInterface()?.getCityWeatherData(city, UrlKeyApi.KEY)?.enqueue(object :
            Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                setData(response.body())
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(applicationContext, "Not City name", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        //GetApi.retrofitService.
        Api.getApiInterface()?.getCurrentWeatherData(latitude, longitude, UrlKeyApi.KEY)?.enqueue(object :
            Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful) {
                    setData(response.body())
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }


    @SuppressLint("SetTextI18n")
    private fun setData(weatherModel: WeatherModel?) {
        binding.mdTvDate.text = DateFormatter.currentDate
        binding.mdTvTime.text = TimeFormatter.currentTime
        binding.mdTvTemp.text = "" + KelvinToCelsius.kelvinToCelsius(weatherModel!!.main.temp) + ""
        //TODO: city
        binding.mdTvCity.text = weatherModel.name
        binding.mdTvWeatherType.text = weatherModel.weather[0].main
        binding.mdTvFeelsLike.text = "" + KelvinToCelsius.kelvinToCelsius(weatherModel.main.feels_like) + ""
        binding.mdTvDayMaxTemp.text = "Day" + KelvinToCelsius.kelvinToCelsius(weatherModel.main.temp_max) + ""
        binding.mdTvDayMinTemp.text = "Night" + KelvinToCelsius.kelvinToCelsius(weatherModel.main.temp_min) + ""
        //
        binding.mdTvNumSunrise.text = Timestamp.timestampToLocalDate(weatherModel.sys.sunrise.toLong())
        binding.mdTvNumSunset.text = Timestamp.timestampToLocalDate(weatherModel.sys.sunset.toLong())
        binding.mdTvNumPressure.text = weatherModel.main.pressure.toString()
        binding.mdTvNumHumidity.text = weatherModel.main.humidity.toString() + "%"
        binding.mdTvNumWindSpeed.text = weatherModel.wind.speed.toString() + "m/s"
        binding.mdTvNumFahrenheit.text = "" + CelsiusToFahrenheit.celsiusToFahrenheit(weatherModel.main.temp) + "F"
        //
        binding.mdTfGetCityName.editText?.setText(weatherModel.name)
        //
        updateImageWeather(weatherModel.weather[0].id)
    }

    private fun updateImageWeather(id: Int) {
        when (id) {
            in 200..232 -> {
                binding.ivImages.setImageResource(R.drawable.ic_thunderstorm)
            }
            in 300..321 -> {
                binding.ivImages.setImageResource(R.drawable.ic_drizzle_)
            }
            in 500..531 -> {
                binding.ivImages.setImageResource(R.drawable.ic_rain)
            }
            in 600..622 -> {
                binding.ivImages.setImageResource(R.drawable.ic_snow)
            }
            in 701..781 -> {
                binding.ivImages.setImageResource(R.drawable.ic_atmosphere)
            }
            800 -> {
                binding.ivImages.setImageResource(R.drawable.ic_img_clear)
            }
            in 801..804 -> {
                binding.ivImages.setImageResource(R.drawable.ic_clouds)
            }
        }
        binding.mdPcProgressLoading.visibility = View.GONE
        binding.coLayout.visibility = View.VISIBLE
    }

    fun bindProgressSizeToViewWidth(circularProgressIndicator: CircularProgressIndicator, view: View?) {
        view!!.post {
            circularProgressIndicator.indicatorSize = view.measuredWidth
        }
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
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
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
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
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