package com.example.weatherapp.data.model.persianCalender

import com.squareup.moshi.Json

data class Timezone(
    @Json(name = "name") val name: String
)