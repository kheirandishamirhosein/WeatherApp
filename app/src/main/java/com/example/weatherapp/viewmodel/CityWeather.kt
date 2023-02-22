package com.example.weatherapp.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.GetApi
import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import kotlinx.coroutines.launch

class CityWeather() : ViewModel() {


    private val _cityWeatherStatus = MutableLiveData<WeatherModel>()
    val cityWeatherStatus: LiveData<WeatherModel> = _cityWeatherStatus

    fun fetchCityWeather(city: String) {
        viewModelScope.launch {
            try {
                GetApi.retrofitService.getCityWeatherData(city, UrlKeyApi.KEY).let { response ->
                    if (response.isSuccessful) {
                        _cityWeatherStatus.postValue(response.body())
                    } else {
                        Log.d("sdsdsd", "Error: ${response.message()}")
                    }
                }
                /*
                _cityWeatherStatus.value =
                    GetApi.retrofitService.getCityWeatherData(city, UrlKeyApi.KEY)
                 */
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}