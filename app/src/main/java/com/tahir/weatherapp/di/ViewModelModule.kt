package com.tahir.weatherapp.di

import androidx.lifecycle.ViewModel
import com.tahir.weatherapp.ui.vm.factory.ViewModelKey
import com.tahir.weatherapp.ui.vm.WeatherListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherListViewModel::class)
    internal abstract fun bindWeatherListViewModel(viewModel: WeatherListViewModel): ViewModel

}