package com.example.weatherapp.ui.airPollution.fragment

import android.Manifest
import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.model.airPollution.AirPollutionList
import com.example.weatherapp.databinding.FragmentAirPollutionBinding
import com.example.weatherapp.ui.airPollution.viewmodel.AirPollutionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class AirPollutionFragment : Fragment() {

    companion object {
        private lateinit var binding: FragmentAirPollutionBinding
        //lateinit var airPollutionListAdapter: AirPollutionListAdapter
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val airPollutionWeatherApiViewModel: AirPollutionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_air_pollution, container, false)
        binding = FragmentAirPollutionBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        getCurrentLocation()
        binding.scrollViewAirPollution.visibility = View.GONE

    }

    //for fetch air pollution
    private fun fetchCurrentLocationAirPollutionWeather(latitude: String, longitude: String) = with(binding) {
        mdPcProgressLoadingAirPollution.visibility = View.VISIBLE
        scrollViewAirPollution.visibility = View.GONE
        airPollutionWeatherApiViewModel.fetchAirPollutionWeatherData(latitude, longitude)
        airPollutionWeatherApiViewModel.airPollutionStatus.observe(viewLifecycleOwner) {
            mdPcProgressLoadingAirPollution.visibility = View.GONE
            scrollViewAirPollution.visibility = View.VISIBLE
            setDataView(it.list)
        }
    }

    //set data view
    @SuppressLint("SetTextI18n")
    fun setDataView(airPollutionWeatherModel: List<AirPollutionList>) = with(binding) {
        //co
        for (i in 0..7) {
            tvCoName.text = "Carbon monoxide"
            tvCoComponent.text = "" + airPollutionWeatherModel[i].components.co + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
            //nh3
            tvNh3Name.text = "Ammonia"
            tvNh3Component.text = "" + airPollutionWeatherModel[i].components.nh3 + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
            //no
            tvNoName.text = "Nitrogen monoxide"
            tvNoComponent.text = "" + airPollutionWeatherModel[i].components.no + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
            //no2
            tvNo2Name.text = "Nitrogen dioxide"
            tvNo2Component.text = "" + airPollutionWeatherModel[i].components.no2 + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
            //o3
            tvO3Name.text = "Ozone"
            tvO3Component.text = "" + airPollutionWeatherModel[i].components.o3 + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
            //pm10
            tvPm10Name.text = "Coarse particulate matter"
            tvPm10Name.isSelected = true
            tvPm10Component.text = "" + airPollutionWeatherModel[i].components.pm10 + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
            //pm2_5
            tvPm25Name.text = "Fine particles matter"
            tvPm25Name.isSelected = true
            tvPm25Component.text = "" + airPollutionWeatherModel[i].components.pm2_5 + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
            //so2
            tvSo2Name.text = "Sulphur dioxide"
            tvSo2Component.text = "" + airPollutionWeatherModel[i].components.so2 + " μg/m3"
            updateFeelingStatusApi(airPollutionWeatherModel[i].main.aqi)
        }
    }


    @SuppressLint("SetTextI18n")
    fun updateFeelingStatusApi(api: Int) = with(binding) {
        when (api) {
            1 -> {
                //co
                tvCoFeelingStatus.text = "Good"
                tvCoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                //nh3
                tvNh3FeelingStatus.text = "Good"
                tvNh3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                //no
                tvNoFeelingStatus.text = "Good"
                tvNoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                //no2
                tvNo2FeelingStatus.text = "Good"
                tvNo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                //o3
                tvO3FeelingStatus.text = "Good"
                tvO3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                //pm10
                tvPm10FeelingStatus.text = "Good"
                tvPm10FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                //pm2_5
                tvPm25FeelingStatus.text = "Good"
                tvPm25FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                //so
                tvSo2FeelingStatus.text = "Good"
                tvSo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            2 -> {
                //co
                tvCoFeelingStatus.text = "Fair"
                tvCoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
                //nh3
                tvNh3FeelingStatus.text = "Fair"
                tvNh3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
                //no
                tvNoFeelingStatus.text = "Fair"
                tvNoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
                //no2
                tvNo2FeelingStatus.text = "Fair"
                tvNo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
                //o3
                tvO3FeelingStatus.text = "Fair"
                tvO3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
                //pm10
                tvPm10FeelingStatus.text = "Fair"
                tvPm10FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
                //pm2_5
                tvPm25FeelingStatus.text = "Fair"
                tvPm25FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
                //so
                tvSo2FeelingStatus.text = "Fair"
                tvSo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.fair))
            }
            3 -> {
                //co
                tvCoFeelingStatus.text = "Moderate"
                tvCoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                //nh3
                tvNh3FeelingStatus.text = "Moderate"
                tvNh3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                //no
                tvNoFeelingStatus.text = "Moderate"
                tvNoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                //no2
                tvNo2FeelingStatus.text = "Moderate"
                tvNo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                //o3
                tvO3FeelingStatus.text = "Moderate"
                tvO3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                //pm10
                tvPm10FeelingStatus.text = "Moderate"
                tvPm10FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                //pm2_5
                tvPm25FeelingStatus.text = "Moderate"
                tvPm25FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
                //so
                tvSo2FeelingStatus.text = "Moderate"
                tvSo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            }
            4 -> {
                //co
                tvCoFeelingStatus.text = "Poor"
                tvCoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                //nh3
                tvNh3FeelingStatus.text = "Poor"
                tvNh3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                //no
                tvNoFeelingStatus.text = "Poor"
                tvNoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                //no2
                tvNo2FeelingStatus.text = "Poor"
                tvNo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                //o3
                tvO3FeelingStatus.text = "Poor"
                tvO3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                //pm10
                tvPm10FeelingStatus.text = "Poor"
                tvPm10FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                //pm2_5
                tvPm25FeelingStatus.text = "Poor"
                tvPm25FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                //so
                tvSo2FeelingStatus.text = "Poor"
                tvSo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
            5 -> {
                //co
                tvCoFeelingStatus.text = "Very Poor"
                tvCoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                //nh3
                tvNh3FeelingStatus.text = "Very Poor"
                tvNh3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                //no
                tvNoFeelingStatus.text = "Very Poor"
                tvNoFeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                //no2
                tvNo2FeelingStatus.text = "Very Poor"
                tvNo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                //o3
                tvO3FeelingStatus.text = "Very Poor"
                tvO3FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                //pm10
                tvPm10FeelingStatus.text = "Very Poor"
                tvPm10FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                //pm2_5
                tvPm25FeelingStatus.text = "Very Poor"
                tvPm25FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                //so
                tvSo2FeelingStatus.text = "Very Poor"
                tvSo2FeelingStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
            }
        }
    }


    /*****get current location an permissions *****/
    private fun getCurrentLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
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
                    requestPermissions()
                    return
                }
                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    // Got last known location. In some rare situations this can be null.
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(context, "Null Receive", Toast.LENGTH_SHORT).show()
                    } else {
                        //TODO: imp codes and fetch location weathers
                        //fetchCurrentLocationFiveDayForecastWeather()
                        fetchCurrentLocationAirPollutionWeather(
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
            requestPermissions()
        }
    }

    //check permission
    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
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
            requireActivity(), arrayOf(
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
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //is Location Enabled(GPS_PROVIDER,NETWORK_PROVIDER)
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as
                LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}