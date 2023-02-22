package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Sys(
    @Json(name = "country") val country: String,
    @Json(name = "id") val id: Int,
    @Json(name = "sunrise") val sunrise: Int,
    @Json(name = "sunset") val sunset: Int,
    @Json(name = "type") val type: Int
)