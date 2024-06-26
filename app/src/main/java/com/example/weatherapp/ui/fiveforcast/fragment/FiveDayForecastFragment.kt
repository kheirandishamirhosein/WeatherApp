package com.example.weatherapp.ui.fiveforcast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.model.forecastLocation.FiveForecastList
import com.example.weatherapp.databinding.FragmentFiveDayForcastBinding
import com.example.weatherapp.ui.fiveforcast.adapter.ForecastListAdapter
import com.example.weatherapp.ui.fiveforcast.viewmodel.FiveDayForecastViewModel
import com.example.weatherapp.util.LocationPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FiveDayForecastFragment : Fragment(), ForecastListAdapter.OnItemClickFiveForecast {

    private lateinit var binding: FragmentFiveDayForcastBinding
    private lateinit var fiveDayForecastListAdapter: ForecastListAdapter

    @Inject
    lateinit var fiveDayForecastWeatherApiViewModel: FiveDayForecastViewModel

    @Inject
    lateinit var locationPermission: LocationPermission

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
        fiveDayForecastWeatherApiViewModel = ViewModelProvider(this)[FiveDayForecastViewModel::class.java]
        getCurrentLocation()
        bindRecyclerview()
    }

    // bind recyclerview
    private fun bindRecyclerview() = with(binding) {
        forecast5DayRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        fiveDayForecastListAdapter = ForecastListAdapter(this@FiveDayForecastFragment)
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
        locationPermission.getCurrentLocation { location ->
            if (location == null) {
                Toast.makeText(context, "Failed to get current location", Toast.LENGTH_SHORT).show()
            } else {
                val latitude = location.latitude
                val longitude = location.longitude
                fetchCurrentLocationFiveDayForecastWeather(latitude.toString(), longitude.toString())
            }
        }
    }


    override fun onItemClick(model: FiveForecastList) {
        Toast.makeText(context, "Item Clicked  !!!", Toast.LENGTH_SHORT).show()
    }

}