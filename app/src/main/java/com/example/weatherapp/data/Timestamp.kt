package com.example.weatherapp.data

import java.time.Instant
import java.time.ZoneId


/*
* A timestamp is a sequence of characters or encoded information identifying when a certain event occurred,
* For example sunrise and sunset
* */
object Timestamp {
    fun timestampToLocalDate(timestamp: Long): String {
        val localTime = timestamp.let {
            Instant.ofEpochSecond(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }
        return localTime.toString()
    }
}