package com.example.weatherapp.api

import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


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
    fun getCurrentWeatherData(
        @Query("let") latitude: String,
        @Query("lon") longitude: String,
        @Query("APPID") api_key: String
    ): Call<WeatherModel>

    @GET("weather")
    fun getCityWeatherData(
        @Query("q") city: String,
        @Query("APPID") api_key: String
    ): Call<WeatherModel>
}

//object api for get retrofit
object GetApi {
    val retrofitService: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}

