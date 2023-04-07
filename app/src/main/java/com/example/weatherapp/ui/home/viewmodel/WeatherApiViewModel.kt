package com.example.weatherapp.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.GetApi
import com.example.weatherapp.data.model.currentLocation.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import kotlinx.coroutines.launch

class WeatherApiViewModel : ViewModel() {

    //current Weather location Status LiveData
    private val _currentWeatherStatus = MutableLiveData<WeatherModel>()
    val currentWeatherStatus: LiveData<WeatherModel> = _currentWeatherStatus

    //city Weather Status LiveData
    private val _cityWeatherStatus = MutableLiveData<WeatherModel>()
    val cityWeatherStatus: LiveData<WeatherModel> = _cityWeatherStatus

    //fetch Current Location Weather (coroutine , api , Livedata)
    fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        //observe viewModel one time , and keep before data
        if (_currentWeatherStatus.value != null) {
            return
        }
        viewModelScope.launch {
            try {
                _currentWeatherStatus.value =
                    GetApi.retrofitService.getCurrentWeatherData(latitude, longitude, UrlKeyApi.KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //fetch CityWeather (coroutine , api , Livedata)
    fun fetchCityWeather(city: String) {
        viewModelScope.launch {
            try {
                _cityWeatherStatus.value =
                    GetApi.retrofitService.getCityWeatherData(city, UrlKeyApi.KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}