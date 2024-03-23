package com.example.weatherapp.ui.airPollution.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.model.airPollution.AirPollutionList
import com.example.weatherapp.databinding.FragmentAirPollutionBinding
import com.example.weatherapp.ui.airPollution.viewmodel.AirPollutionViewModel
import com.example.weatherapp.util.LocationPermission
import com.google.android.gms.location.LocationServices

class AirPollutionFragment : Fragment() {

    private lateinit var binding: FragmentAirPollutionBinding
    private val airPollutionWeatherApiViewModel: AirPollutionViewModel by viewModels()
    private lateinit var locationPermission: LocationPermission

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
        locationPermission = LocationPermission(requireActivity())
        getCurrentLocation()
        binding.scrollViewAirPollution.visibility = View.GONE

    }

    //for fetch air pollution
    private fun fetchCurrentLocationAirPollutionWeather(latitude: String, longitude: String) = with(binding) {
        mdPcProgressLoadingAirPollution.visibility = View.VISIBLE
        airPollutionWeatherApiViewModel.fetchAirPollutionWeatherData(latitude, longitude)
        airPollutionWeatherApiViewModel.airPollutionStatus.observe(viewLifecycleOwner) {
            mdPcProgressLoadingAirPollution.visibility = View.GONE
            scrollViewAirPollution.visibility = View.VISIBLE
            setDataView(it.list)
        }
    }

    //set data view
    @SuppressLint("SetTextI18n")
    fun setDataView(airPollution: List<AirPollutionList>) = with(binding) {
        val airPollution = airPollution[0]

        //co
        tvCoName.text = "Carbon monoxide"
        tvCoComponent.text = "" + airPollution.components.co + " μg/m3"
        tvCoNumInCircle.text = "" + ((airPollution.components.co / 15400) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)
        //nh3
        tvNh3Name.text = "Ammonia"
        tvNh3Component.text = "" + airPollution.components.nh3 + " μg/m3"
        tvNh3NumInCircle.text = "" + ((airPollution.components.nh3 / 200) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)
        //no
        tvNoName.text = "Nitrogen monoxide"
        tvNoComponent.text = "" + airPollution.components.no + " μg/m3"
        tvNoNumInCircle.text = "" + ((airPollution.components.no / 300) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)
        //no2
        tvNo2Name.text = "Nitrogen dioxide"
        tvNo2Component.text = "" + airPollution.components.no2 + " μg/m3"
        tvNo2NumInCircle.text = "" + ((airPollution.components.no2 / 200) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)
        //o3
        tvO3Name.text = "Ozone"
        tvO3Component.text = "" + airPollution.components.o3 + " μg/m3"
        tvO3NumInCircle.text = "" + ((airPollution.components.o3 / 180) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)
        //pm10
        tvPm10Name.text = "Coarse particulate matter"
        tvPm10Name.isSelected = true
        tvPm10Component.text = "" + airPollution.components.pm10 + " μg/m3"
        tvPm10NumInCircle.text = "" + ((airPollution.components.pm10 / 200) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)
        //pm2_5
        tvPm25Name.text = "Fine particles matter"
        tvPm25Name.isSelected = true
        tvPm25Component.text = "" + airPollution.components.pm2_5 + " μg/m3"
        tvPm25NumInCircle.text = "" + ((airPollution.components.pm2_5 / 200) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)
        //so2
        tvSo2Name.text = "Sulphur dioxide"
        tvSo2Component.text = "" + airPollution.components.so2 + " μg/m3"
        tvSo2NumInCircle.text = "" + ((airPollution.components.so2 / 350) * 100).toInt() + "%"
        updateFeelingStatusApi(airPollution.main.aqi)

        /*** pb circle diagram ***/
        //co
        pbCoCircleDiagram.apply {
            progress = airPollution.components.co.toInt()
            max = 15400
        }
        //nh3
        pbNh3CircleDiagram.apply {
            progress = airPollution.components.nh3.toInt()
            max = 200
        }
        //no
        pbNoCircleDiagram.apply {
            progress = airPollution.components.no.toInt()
            max = 300
        }
        //no2
        pbNo2CircleDiagram.apply {
            progress = airPollution.components.no2.toInt()
            max = 200
        }
        //o3
        pbO3CircleDiagram.apply {
            progress = airPollution.components.o3.toInt()
            max = 180
        }
        //pm10
        pbPm10CircleDiagram.apply {
            progress = airPollution.components.pm10.toInt()
            max = 200
        }
        //pm2_5
        pbPm25CircleDiagram.apply {
            progress = airPollution.components.pm2_5.toInt()
            max = 200
        }
        //so2
        pbSo2CircleDiagram.apply {
            progress = airPollution.components.so2.toInt()
            max = 350
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

    private fun getCurrentLocation() {
        locationPermission.getCurrentLocation { location ->
            if (location == null) {
                Toast.makeText(context, "Failed to get current location", Toast.LENGTH_SHORT).show()
            } else {
                val latitude = location.latitude
                val longitude = location.longitude
                fetchCurrentLocationAirPollutionWeather(latitude.toString(), longitude.toString())
            }
        }
    }

}