package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Season(
    @Json(name = "name") val name: String
)