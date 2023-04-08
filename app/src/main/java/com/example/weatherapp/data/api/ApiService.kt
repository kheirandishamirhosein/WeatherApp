package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.airPollution.AirPollution
import com.example.weatherapp.data.model.currentLocation.WeatherModel
import com.example.weatherapp.data.model.forecastLocation.FiveForecastWeatherModel
import com.example.weatherapp.util.UrlKeyApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//retrofit
private val retrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(UrlKeyApi.BASE_URL)
    .build()

// interface for get Api
interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("APPID") api_key: String
    ): WeatherModel

    @GET("weather")
    suspend fun getCityWeatherData(
        @Query("q") city: String,
        @Query("APPID") api_key: String
    ): WeatherModel

    @GET("forecast")
    suspend fun getFiveDayForecastWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("APPID") api_key: String
    ): FiveForecastWeatherModel

    @GET("air_pollution")
    suspend fun getAirPollutionWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("APPID") api_key: String
    ): AirPollution
}

//object api for get retrofit
object GetApi {
    val retrofitService: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}