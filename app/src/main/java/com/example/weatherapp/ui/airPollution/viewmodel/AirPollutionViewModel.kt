package com.example.weatherapp.ui.airPollution.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.GetApi
import com.example.weatherapp.data.model.airPollution.AirPollution
import com.example.weatherapp.util.UrlKeyApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class AirPollutionViewModel @Inject constructor() : ViewModel() {
    private val _airPollutionStatus = MutableLiveData<AirPollution>()
    val airPollutionStatus: LiveData<AirPollution> = _airPollutionStatus

    fun fetchAirPollutionWeatherData(latitude: String, longitude: String) {
        viewModelScope.launch {
            try {
                _airPollutionStatus.value =
                    GetApi.retrofitService.getAirPollutionWeatherData(latitude, longitude, UrlKeyApi.KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}