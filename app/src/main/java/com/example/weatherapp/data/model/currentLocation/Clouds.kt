package com.example.weatherapp.data.model.currentLocation

import com.squareup.moshi.Json

data class Clouds(
    @Json(name = "all")val all: Int
)