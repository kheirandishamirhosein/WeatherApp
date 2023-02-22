package com.example.weatherapp.api

import com.example.weatherapp.models.WeatherModel
import com.example.weatherapp.util.UrlKeyApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
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
    suspend fun getCurrentWeatherData(
        @Query("let") latitude: String,
        @Query("lon") longitude: String,
        @Query("APPID") api_key: String
    ): Response<WeatherModel>

    @GET("weather")
    suspend fun getCityWeatherData(
        @Query("q") city: String,
        @Query("APPID") api_key: String
    ): Response<WeatherModel>
}


//object api for get retrofit
object GetApi {
    val retrofitService: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}


/*
object Api {
    private var retrofit: Retrofit? = null
    fun getApiInterface(): ApiInterface? {
        if (retrofit == null) {
            retrofit =
                Retrofit.Builder()
                    .baseUrl(UrlKeyApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!.create(ApiInterface::class.java)
    }
}

 */


