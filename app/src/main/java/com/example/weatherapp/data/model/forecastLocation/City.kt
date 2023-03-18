package com.example.weatherapp.data.model.forecastLocation

import com.squareup.moshi.Json

data class City(
    @Json(name = "coord") val coord: Coord,
    @Json(name = "country") val country: String,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    //@Json(name = "id") val timezone: Int
)