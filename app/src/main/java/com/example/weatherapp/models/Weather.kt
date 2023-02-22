package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Weather(
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String
)