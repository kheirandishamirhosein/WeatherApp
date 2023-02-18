package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import kotlinx.coroutines.launch
import retrofit2.Call

class CityWeather: ViewModel() {
/*
    private val _cityWeatherStatus = MutableLiveData<WeatherModel?>()
    val cityWeatherStatus: LiveData<WeatherModel?> = _cityWeatherStatus

    fun fetchCityWeather(city: String) {
        viewModelScope.launch {
            try {
                _cityWeatherStatus.value =
                    GetApi.retrofitService.getCityWeatherData(city,UrlKeyApi.KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
 */
}