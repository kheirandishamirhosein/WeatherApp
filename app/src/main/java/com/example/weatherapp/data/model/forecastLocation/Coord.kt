package com.example.weatherapp.data.model.forecastLocation

import com.squareup.moshi.Json

data class Coord(
    @Json(name = "lat") val lat: Double,
    @Json(name = "lon") val lon: Double
)