package com.tahir.weatherapp.di

import android.content.Context
import com.tahir.weatherapp.core.data.di.DatabaseModule
import com.tahir.weatherapp.core.data.di.NetworkModule
import com.tahir.weatherapp.core.data.di.RepositoryModule
import com.tahir.weatherapp.core.domain.di.InteractorModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        SubComponentModule::class,
        SettingsModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance the Context instance passed in will be available throughout the graph
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun homeComponent(): HomeComponent.Factory

}