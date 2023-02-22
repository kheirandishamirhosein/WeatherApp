package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.GetApi
import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import kotlinx.coroutines.launch

class CurrentLocation : ViewModel() {

    private val _currentWeatherStatus = MutableLiveData<WeatherModel>()
    val currentWeatherStatus: LiveData<WeatherModel> = _currentWeatherStatus

    fun fetchCurrentLocationWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            try {
                GetApi.retrofitService.getCurrentWeatherData(latitude, longitude, UrlKeyApi.KEY).let { response ->
                    if (response.isSuccessful) {
                        _currentWeatherStatus.postValue(response.body())
                    } else {
                        Log.d("sdsdsd", "Error: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}