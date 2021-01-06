package com.tahir.weatherapp.core.domain.di

import com.tahir.weatherapp.core.domain.interactors.GetWeatherInteractor
import com.tahir.weatherapp.core.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun providesGetWeatherInteractor(weatherRepository: WeatherRepository): GetWeatherInteractor {
        return GetWeatherInteractor(weatherRepository)
    }

}