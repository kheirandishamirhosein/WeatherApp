package com.example.weatherapp.data.model.forecastLocation

import com.squareup.moshi.Json

data class Main(
    @Json(name = "feels_like") val feels_like: Double,
    @Json(name = "temp") val temp: Double,
    //@Json(name = "temp_kf") val temp_kf: Double,
)