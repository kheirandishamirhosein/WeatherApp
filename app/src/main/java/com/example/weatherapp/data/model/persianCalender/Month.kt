package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Month(
    val asterism: String,
    @Json(name = "name") val name: String,
    val number: Number
)