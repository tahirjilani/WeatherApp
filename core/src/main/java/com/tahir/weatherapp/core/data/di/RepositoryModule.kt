package com.tahir.weatherapp.core.data.di

import com.tahir.weatherapp.core.data.db.WeatherDao
import com.tahir.weatherapp.core.data.network.Webservices
import com.tahir.weatherapp.core.data.repository.WeatherRepositoryImpl
import com.tahir.weatherapp.core.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesGetWeatherRepository(webservices: Webservices, weatherDao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(webservices, weatherDao)
    }

}