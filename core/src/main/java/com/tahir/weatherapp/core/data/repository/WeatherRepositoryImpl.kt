package com.tahir.weatherapp.core.data.repository

import com.tahir.weatherapp.core.data.db.WeatherDao
import com.tahir.weatherapp.core.data.db.WeatherEntity
import com.tahir.weatherapp.core.data.network.Webservices
import com.tahir.weatherapp.core.domain.model.WeatherInfo
import com.tahir.weatherapp.core.domain.model.Resource
import com.tahir.weatherapp.core.domain.repository.WeatherRepository
import java.lang.Exception

class WeatherRepositoryImpl constructor(
    private val webservices: Webservices,
    private val weatherDao: WeatherDao
    ): WeatherRepository() {
    override suspend fun getWeatherForLocation(lat: Double, lon: Double): Resource<WeatherInfo> {

        return try {
            val response = webservices.getWeather(lat, lon)
            if (response.isSuccessful) {

                val weatherInfo = response.body()
                cacheData(weatherInfo)

                Resource.Success(response.body())
            } else {
                //try reading from db as fail over
                getCacheData(Exception(response.message()))
            }
        }catch (ex: Exception){
            //try reading from db as fail over
            return getCacheData(ex)
        }
    }

    /**
     * Get cached resource if any otherwise return exception
     */
    private suspend fun getCacheData(ex: Exception): Resource<WeatherInfo> {
        val cachedList = weatherDao.getAll()
        return if (!cachedList.isNullOrEmpty()){
            Resource.Success(cachedList[0].weatherInfo, ex.message)
        }else {
            Resource.Error(ex)
        }
    }

    /**
     * Save data in room database
     */
    private suspend fun cacheData(weatherInfo: WeatherInfo?){
        weatherInfo?.let {
            try {
                //lets replace existing record by passing same id, we can clear data before we insert
                //fresh row but in case of failure in writing, we will lost cached data, so lets
                //do this trick
                weatherDao.insert(WeatherEntity(1, weatherInfo))
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }
}