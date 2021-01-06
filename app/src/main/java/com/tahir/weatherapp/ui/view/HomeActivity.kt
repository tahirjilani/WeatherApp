package com.tahir.weatherapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tahir.weatherapp.App
import com.tahir.weatherapp.R
import com.tahir.weatherapp.di.HomeComponent
import com.tahir.weatherapp.utils.AppIdlingResource

class HomeActivity : AppCompatActivity() {

    lateinit var homeComponent: HomeComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppIdlingResource.increment()

        homeComponent = (application as App).appComponent.homeComponent().create()
        homeComponent.inject(this)

        setContentView(R.layout.activity_main)
    }
}