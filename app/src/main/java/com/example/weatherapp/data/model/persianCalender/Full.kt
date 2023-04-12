package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Full(
    @Json(name = "official") val official: Official,
)