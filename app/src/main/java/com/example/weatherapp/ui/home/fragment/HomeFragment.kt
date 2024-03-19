package com.example.weatherapp.ui.home.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.model.currentLocation.WeatherModel
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.ui.home.viewmodel.WeatherApiViewModel
import com.example.weatherapp.util.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val weatherApiViewModel: WeatherApiViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationPermission: LocationPermission

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationPermission = LocationPermission(requireActivity())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (weatherApiViewModel.currentWeatherStatus.value == null) {
            getCurrentLocation()
            binding.mdPcProgressLoading.visibility = View.VISIBLE
            binding.coLayout.visibility = View.GONE
        }

        weatherApiViewModel.currentWeatherStatus.observe(viewLifecycleOwner) { setDataView(it) }
        weatherApiViewModel.cityWeatherStatus.observe(viewLifecycleOwner) { setDataView(it) }

        binding.textFieldEdit.setOnEditorActionListener { _, actionId, _ ->
            binding.mdPcProgressLoading.visibility = View.VISIBLE
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //TODO: imp codes city edit text
                getCityWeather(binding.textFieldEdit.text.toString().trim())
                val view = getView()?.findFocus()
                if (view != null) {
                    val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    binding.textFieldEdit.clearFocus()
                }
                true
            } else false
        }
    }

    @SuppressLint("SetTextI18n")
    fun setDataView(weatherModel: WeatherModel?) = with(binding) {
        mdTvDate.text = DateFormatter.currentDate
        mdTvTime.text = TimeFormatter.currentTime
        mdTvTemp.text = "" + KelvinToCelsius.kelvinToCelsius(weatherModel!!.main.temp) + ""
        mdTvCity.text = weatherModel.name
        mdTvCity.isSelected = true
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

    //update Images Weather
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

    // get City Weather
    private fun getCityWeather(city: String) {
        binding.mdPcProgressLoading.visibility = View.VISIBLE
        weatherApiViewModel.fetchCityWeather(city)
        //Toast.makeText(this,"City Not Found",Toast.LENGTH_SHORT).show()
    }

    //fetch Current Location Weather latitude , longitude
    private fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        weatherApiViewModel.fetchCurrentLocationWeather(latitude, longitude)
    }

    /***permissions***/
    /*** imps all permissions in fun getCurrentLocation ***/
    private fun getCurrentLocation() {
        if (locationPermission.checkLocationPermission()) {
            if (locationPermission.isLocationEnabled()) {
                // latitude and longitude
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermission.checkLocationPermission()
                    return
                }
                fusedLocationClient.lastLocation
                    .addOnCompleteListener(requireActivity()) { task ->
                        // Got last known location. In some rare situations this can be null.
                        val location: Location? = task.result
                        if (location == null) {
                            Toast.makeText(requireContext(), "Null Receive", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            //TODO: imp codes and fetch location weathers
                            fetchCurrentLocationWeather(
                                location.latitude.toString(),
                                location.longitude.toString()
                            )
                        }
                    }
            } else {
                //setting
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            //request permission
            locationPermission.requestLocationPermission()
        }
    }

}