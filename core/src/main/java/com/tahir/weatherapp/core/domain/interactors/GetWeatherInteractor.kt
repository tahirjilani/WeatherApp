package com.tahir.weatherapp.core.domain.interactors

import com.tahir.weatherapp.core.domain.repository.WeatherRepository

class GetWeatherInteractor constructor(private val weatherRepository: WeatherRepository) {
  suspend operator fun invoke(lat: Double, long: Double) = weatherRepository.getWeatherForLocation(lat, long)
}