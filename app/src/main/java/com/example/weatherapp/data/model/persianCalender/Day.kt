package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Day(
    val events: Events,
    @Json(name = "name") val name: String,
    val number: Number
)