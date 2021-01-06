package com.tahir.weatherapp.core.data.network

import com.tahir.weatherapp.core.BuildConfig
import com.tahir.weatherapp.core.domain.model.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Webservices {
  
  @GET("onecall")
  suspend fun getWeather(
    @Query("lat") lat: Double,
    @Query("lon") lon: Double,
    @Query("exclude") exclude: String = "minutely",
    @Query("appid") apiKey: String = BuildConfig.API_KEY
  ): Response<WeatherInfo>
}