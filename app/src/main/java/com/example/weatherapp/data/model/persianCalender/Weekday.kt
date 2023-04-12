package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Weekday(
    val champ: String,
    @Json(name = "name") val name: String,
    val number: Number
)