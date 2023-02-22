package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Rain(
    @Json(name = "`1h`") val `1h`: Double
)