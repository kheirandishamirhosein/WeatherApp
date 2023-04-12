package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Usual(
    @Json(name = "en") val en: String,
    @Json(name = "fa") val fa: String
)