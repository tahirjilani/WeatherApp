package com.tahir.weatherapp.core.domain.model

import java.io.Serializable

data class WeatherInfo(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val current: WeatherSummary,
    val hourly: List<WeatherSummary>,
    val daily: List<DailySummary>,
    val cod: Int,
    val message: String
) {
    data class WeatherSummary(
        val dt: Long,
        val sunrise: Int,
        val sunset: Int,
        val temp: Double,
        val feels_like: Double,
        val pressure: Int,
        val humidity: Int,
        val dew_point: Double,
        val clouds: Int,
        val visibility: Int,
        val wind_speed: Double,
        val wind_deg: Int,
        val weather: List<Weather>
    )

    data class DailySummary(
        val dt: Long,
        val sunrise: Long,
        val sunset: Long,
        val temp: Temp,
        val feels_like: FeelsLike,
        val pressure: Int,
        val humidity: Int,
        val dew_point: Double,
        val wind_speed: Double,
        val wind_deg: Int,
        val weather: List<Weather>,
        val clouds: String,
        val pop: String
    ):Serializable {
        data class Temp(
            val day: Double,
            val min: Double,
            val max: Double,
            val night: Double,
            val eve: Double,
            val morn: Double
        ):Serializable

        data class FeelsLike(
            val day: Double,
            val night: Double,
            val eve: Double,
            val morn: Double
        ):Serializable

    }

    data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    ):Serializable
}