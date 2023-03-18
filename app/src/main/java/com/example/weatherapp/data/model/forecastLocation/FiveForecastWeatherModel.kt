package com.example.weatherapp.data.model.forecastLocation

import com.squareup.moshi.Json

data class FiveForecastWeatherModel(
    @Json(name = "city") val city: City,
    @Json(name = "cnt") val cnt: Int,
    @Json(name = "cod") val cod: String,
    @Json(name = "list") val list: List<FiveForecastList>,
    @Json(name = "message") val message: Int,
)