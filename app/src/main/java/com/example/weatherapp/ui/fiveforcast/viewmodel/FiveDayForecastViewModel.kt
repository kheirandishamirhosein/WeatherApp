package com.example.weatherapp.ui.fiveforcast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.GetApi
import com.example.weatherapp.data.model.forecastLocation.FiveForecastList
import com.example.weatherapp.data.model.forecastLocation.FiveForecastWeatherModel
import com.example.weatherapp.util.UrlKeyApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class FiveDayForecastViewModel @Inject constructor() : ViewModel() {

    private val _fiveDayForecastStatus = MutableLiveData <FiveForecastWeatherModel>()
    val fiveDayForecastStatus: LiveData <FiveForecastWeatherModel> = _fiveDayForecastStatus

    fun fetchFiveDayForecastWeatherData(latitude: String, longitude: String) {
        viewModelScope.launch {
            try {
                _fiveDayForecastStatus.value =
                    GetApi.retrofitService.getFiveDayForecastWeatherData(latitude, longitude, UrlKeyApi.KEY)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}