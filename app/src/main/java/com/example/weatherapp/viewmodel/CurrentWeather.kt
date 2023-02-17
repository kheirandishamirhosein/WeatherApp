package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.GetApi
import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import kotlinx.coroutines.launch
import retrofit2.Call

class CurrentWeather : ViewModel() {

    /*
    private val _currentWeatherStatus = MutableLiveData<WeatherModel?>()
    val currentWeatherStatus: LiveData<WeatherModel?> = _currentWeatherStatus

    fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            try {
                _currentWeatherStatus.value =
                    GetApi.retrofitService.getCurrentWeatherData(latitude, longitude, UrlKeyApi.KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

     */
}