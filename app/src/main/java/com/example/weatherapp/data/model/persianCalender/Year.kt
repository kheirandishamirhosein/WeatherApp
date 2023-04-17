package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Year(
    @Json(name = "animal") val animal: String,
    @Json(name = "name") val name: String
)