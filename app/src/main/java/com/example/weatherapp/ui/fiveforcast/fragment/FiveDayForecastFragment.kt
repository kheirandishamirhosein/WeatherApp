package com.example.weatherapp.ui.fiveforcast.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentFiveDayForcastBinding
import com.example.weatherapp.ui.fiveforcast.adapter.ForecastListAdapter
import com.example.weatherapp.ui.fiveforcast.viewmodel.FiveDayForecastViewModel
import com.example.weatherapp.util.LocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FiveDayForecastFragment : Fragment() {

    private lateinit var binding: FragmentFiveDayForcastBinding
    lateinit var fiveDayForecastListAdapter: ForecastListAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val fiveDayForecastWeatherApiViewModel: FiveDayForecastViewModel by viewModels()
    private lateinit var locationPermission: LocationPermission

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_five_day_forcast, container, false)
        binding = FragmentFiveDayForcastBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationPermission = LocationPermission(requireActivity())
        fusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        getCurrentLocation()
        bindRecyclerview()
    }

    // bind recyclerview
    private fun bindRecyclerview() = with(binding) {
        forecast5DayRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        fiveDayForecastListAdapter = ForecastListAdapter()
        forecast5DayRecyclerview.adapter = fiveDayForecastListAdapter
    }

    // observe viewModel
    private fun fetchCurrentLocationFiveDayForecastWeather(latitude: String, longitude: String) = with(binding) {
        mdPcProgressLoadingForecast5Day.visibility = View.VISIBLE
        fiveDayForecastWeatherApiViewModel.fetchFiveDayForecastWeatherData(latitude, longitude)
        fiveDayForecastWeatherApiViewModel.fiveDayForecastStatus.observe(viewLifecycleOwner) {
            mdPcProgressLoadingForecast5Day.visibility = View.GONE
            fiveDayForecastListAdapter.setItem(it.list)
        }
    }

    private fun getCurrentLocation() {
        if (locationPermission.checkLocationPermission()) {
            if (locationPermission.isLocationEnabled()) {
                // latitude and longitude
                if (context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED && context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermission.requestLocationPermission()
                    return
                }
                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    // Got last known location. In some rare situations this can be null.
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(context, "Null Receive", Toast.LENGTH_SHORT).show()
                    } else {
                        //TODO: imp codes and fetch location weathers
                        fetchCurrentLocationFiveDayForecastWeather(
                            location.latitude.toString(),
                            location.longitude.toString()
                        )

                    }
                }
            } else {
                //setting
                Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            //request permission
            locationPermission.requestLocationPermission()
        }
    }

}