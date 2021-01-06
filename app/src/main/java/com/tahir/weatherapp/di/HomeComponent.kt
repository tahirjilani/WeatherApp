package com.tahir.weatherapp.di

import com.tahir.weatherapp.ui.view.HomeActivity
import com.tahir.weatherapp.ui.view.WeatherListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface HomeComponent {

    // Factory that is used to create instances of this subComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(homeActivity: HomeActivity)
    fun inject(weatherListFragment: WeatherListFragment)

}