package com.example.weatherapp.data.model.currentLocation

import com.squareup.moshi.Json

data class Wind(
    @Json(name = "deg") val deg: Int,
    @Json(name = "speed") val speed: Double
)