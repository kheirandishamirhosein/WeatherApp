package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Clouds(
    @Json(name = "all")val all: Int
)