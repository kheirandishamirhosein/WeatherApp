package com.example.weatherapp.data.model.forecastLocation

import com.squareup.moshi.Json

data class Weather(
    //@Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String
)