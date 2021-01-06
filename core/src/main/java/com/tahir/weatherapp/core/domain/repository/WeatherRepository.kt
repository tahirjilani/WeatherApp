package com.tahir.weatherapp.core.domain.repository

import com.tahir.weatherapp.core.domain.model.WeatherInfo
import com.tahir.weatherapp.core.domain.model.Resource

abstract class WeatherRepository {
    abstract suspend fun getWeatherForLocation(lat: Double, lon: Double): Resource<WeatherInfo>
}