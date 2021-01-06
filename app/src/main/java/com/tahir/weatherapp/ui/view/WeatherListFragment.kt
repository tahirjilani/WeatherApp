package com.tahir.weatherapp.ui.view

import android.content.Context
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tahir.weatherapp.ui.vm.factory.ViewModelFactory
import com.tahir.weatherapp.R
import com.tahir.weatherapp.databinding.FragmentWeatherListBinding
import com.tahir.weatherapp.core.domain.model.Resource
import com.tahir.weatherapp.core.domain.model.WeatherInfo
import com.tahir.weatherapp.ui.view.adapter.DailyWeatherListAdapter
import com.tahir.weatherapp.ui.view.adapter.HourlyWeatherAdapter
import com.tahir.weatherapp.ui.view.adapter.OnDaySelectListener
import com.tahir.weatherapp.ui.vm.WeatherListViewModel
import com.tahir.weatherapp.utils.*
import java.util.*
import javax.inject.Inject


class WeatherListFragment : LocationAwareFragment(R.layout.fragment_weather_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var weatherListViewModel: WeatherListViewModel

    private lateinit var fragmentBinding: FragmentWeatherListBinding

    //layout managers for recycler views
    private var hourlyViewManager: LinearLayoutManager? = null
    private var dailyViewManager: LinearLayoutManager? = null

    //weather info fetched from data source
    private var weatherInfo: WeatherInfo? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as HomeActivity).homeComponent.inject(this)

        weatherListViewModel = ViewModelProvider(this, viewModelFactory)[WeatherListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentWeatherListBinding.bind(view)
        fragmentBinding = binding

        binding.swipeRefreshLayout.setOnRefreshListener {
            getWeatherForCurrentLocation()
        }
        //lets show loading progress by default
        binding.swipeRefreshLayout.isRefreshing = true

        binding.settingIV.setOnClickListener {
            menuClicked(it)
        }

    }

    override fun onLocationFound(lat: Double, lon: Double) {
        getWeatherData(lat, lon)
    }


    /**
     * Fetch weather data from cloud or db as fail-over
     */
    private fun getWeatherData(lat: Double, lon: Double) {

        weatherListViewModel.getWeather(lat, lon).observe(viewLifecycleOwner, { resource ->
            fragmentBinding.swipeRefreshLayout.isRefreshing = resource is Resource.Loading
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { weather ->

                        AppIdlingResource.decrement()

                        weatherInfo = weather
                        showWeatherData(weather)
                    } ?: run {
                        Toast.makeText(requireContext(),
                                R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
                    }
                    resource.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.exception.message
                            ?: getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        })
    }

    /**
     * Populate UI
     */
    private fun showWeatherData(weatherInfo: WeatherInfo) {

        //show current weather section
        showCurrentWeather(weatherInfo)

        //show hourly weather in horizontal recycler view
        showHourlyWeather(weatherInfo)

        //show weekly weather list
        showWeekWeather(weatherInfo)
    }

    /**
     * Show current weather section
     */
    private fun showCurrentWeather(weatherInfo: WeatherInfo){
        fragmentBinding.apply {
            timeZoneTV.text = weatherInfo.timezone
            weatherInfo.current.let { summary ->
                if (!summary.weather.isNullOrEmpty()) {
                    val currentWeather = summary.weather[0]
                    descriptionTV.text = currentWeather.description.capitalize(Locale.US)
                    ImageUtils.load(requireContext(), currentIconIV, currentWeather.icon)
                }

                temperatureNowTV.text = weatherListViewModel.getTemperature(summary.temp)
                val isCelsius = weatherListViewModel.getIsCelsius()

                val feelsLike = TemperatureUtils.getTemperature(summary.feels_like, isCelsius)
                val feelsLikeText = String.format(getString(R.string.feels_like_text), feelsLike)
                feelsLikeTV.text = feelsLikeText

                var humidityText = String.format(getString(R.string.humidity_text), summary.humidity.toString())
                humidityText = "$humidityText%"
                humidityTV.text = humidityText

                val windText = String.format(getString(R.string.wind_text), summary.wind_speed.toString())
                windSpeedTV.text = windText

                val visibilityText = String.format(getString(R.string.visibility_text), (summary.visibility/1000).toString())
                visibilityTV.text = visibilityText
            }
        }
    }


    /**
     * Show hourly weather forecast for today
     */
    private fun showHourlyWeather(weatherInfo: WeatherInfo){
        //lets create hourly adapter again for simplicity
        val isCelsius = weatherListViewModel.getIsCelsius()
        val hourlyWeatherAdapter = HourlyWeatherAdapter(weatherInfo.hourly, isCelsius)

        fragmentBinding.hourlyRecyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            adapter = hourlyWeatherAdapter

            if (hourlyViewManager == null) {
                hourlyViewManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                layoutManager = hourlyViewManager
                setHasFixedSize(true)
                addItemDecoration(SpacesItemDecoration(12.dpToPx(), true))
            }
        }

    }

    /**
     * Show list of daily weather
     */
    private fun showWeekWeather(weatherInfo: WeatherInfo){
        //lets create daily adapter again for simplicity otherwise we'll have to convert
        // weatherInfo.daily mutable list to change items and notifyDataSetChanged etc.
        val isCelsius = weatherListViewModel.getIsCelsius()

        val dailyWeatherListAdapter = DailyWeatherListAdapter(weatherInfo.daily, isCelsius, object : OnDaySelectListener{
            override fun onDaySelected(dailySummary: WeatherInfo.DailySummary) {
                openDetailPage(weatherInfo.current.temp, dailySummary, isCelsius)
            }
        })

        fragmentBinding.dailyRecyclerView.apply {
            adapter = dailyWeatherListAdapter

            if (dailyViewManager == null) {
                dailyViewManager = LinearLayoutManager(requireContext())
                layoutManager = dailyViewManager
                setHasFixedSize(true)

                val decorator = DividerItemDecoration(this.context, dailyViewManager!!.orientation)
                addItemDecoration(decorator)
            }
        }
    }

    /**
     * Open weather detail screen
     */
    private fun openDetailPage(temp: Double, dailySummary: WeatherInfo.DailySummary, isCelsius: Boolean){
        WeatherDetailActivity.getIntent(requireContext(), temp, dailySummary, isCelsius).apply {
            AppIdlingResource.increment()
            startActivity(this)
        }
    }

    /**
     * Open setting menu
     */
    private fun menuClicked(anchorView: View){

        val popup = PopupMenu(requireContext(), anchorView)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.celsius -> {
                    weatherListViewModel.setIsCelsius(true)
                    updateWeatherUI()
                    true
                }
                R.id.fahrenheit -> {
                    weatherListViewModel.setIsCelsius(false)
                    updateWeatherUI()
                    true
                }
                else -> false
            }
        }
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.settings_menu, popup.menu)
        popup.menu.also {
            when (weatherListViewModel.getIsCelsius()) {
                true -> {
                    it.findItem(R.id.celsius)?.isChecked = true
                }
                else ->{
                    it.findItem(R.id.fahrenheit)?.isChecked = true
                }
            }
        }
        popup.show()
    }

    /**
     * Something has changed, refresh weather UI
     */
    private fun updateWeatherUI(){
        weatherInfo?.let {
            showWeatherData(it)
        }
    }

}