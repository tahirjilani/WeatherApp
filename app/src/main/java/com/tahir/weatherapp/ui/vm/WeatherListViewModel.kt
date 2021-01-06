package com.tahir.weatherapp.ui.vm

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tahir.weatherapp.core.domain.interactors.GetWeatherInteractor
import com.tahir.weatherapp.core.domain.model.Resource
import com.tahir.weatherapp.preference.AppPreferences
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeatherListViewModel @Inject constructor(
    private val getWeatherInteractor: GetWeatherInteractor,
    private val appPreferences: AppPreferences
    ) : SettingsViewModel(appPreferences) {

    /**
     * Lets call network IO and database access on Dispatchers.IO
     */
    fun getWeather(lat: Double, lon: Double) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            emit(getWeatherInteractor.invoke(lat, lon))
        }
}