package com.tahir.weatherapp.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tahir.weatherapp.R
import com.tahir.weatherapp.databinding.ActivityWeatherDetailBinding
import com.tahir.weatherapp.core.domain.model.WeatherInfo.DailySummary
import com.tahir.weatherapp.utils.AppIdlingResource
import com.tahir.weatherapp.utils.DateUtils
import com.tahir.weatherapp.utils.ImageUtils
import com.tahir.weatherapp.utils.TemperatureUtils
import java.util.*

private const val PARAM_DETAIL = "detail_param"
private const val PARAM_TEMP = "temp_param"
private const val PARAM_IS_CELSIUS = "cel_param"
class WeatherDetailActivity : AppCompatActivity() {

    companion object{
        fun getIntent(context: Context, temp: Double, weather: DailySummary, isCelsius: Boolean): Intent{
            return Intent(context, WeatherDetailActivity::class.java).apply {
                this.putExtra(PARAM_TEMP, temp)
                this.putExtra(PARAM_TEMP, temp)
                this.putExtra(PARAM_DETAIL, weather)
                this.putExtra(PARAM_IS_CELSIUS, isCelsius)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityWeatherDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather_detail)
        setSupportActionBar(binding.toolbar)

        binding.backImageView.setOnClickListener {
            finish()
        }

        intent.extras?.let { bundle ->
            bundle.getSerializable(PARAM_DETAIL)?.apply{
                val weather = this as DailySummary

                val temp = bundle.getDouble(PARAM_TEMP)
                val isCelsius = bundle.getBoolean(PARAM_IS_CELSIUS)
                showWeatherDetails(binding, temp, weather, isCelsius)

                AppIdlingResource.decrement()
            }
        }
    }

    /**
     * Show weather details.
     */
    private fun showWeatherDetails(binding: ActivityWeatherDetailBinding, temp: Double, dailySummary: DailySummary, isCelsius: Boolean){
        binding.apply {
            dateTV.text = DateUtils.getDateTimeYear(dailySummary.dt)

            if (!dailySummary.weather.isNullOrEmpty()) {
                val dayWeather = dailySummary.weather[0]
                descriptionTV.text = dayWeather.description.capitalize(Locale.US)
                ImageUtils.load(this@WeatherDetailActivity, currentIconIV, dayWeather.icon)
            }

            temperatureTV.text = TemperatureUtils.getTemperature(temp, isCelsius)

            val minTemp = TemperatureUtils.getTemperature(dailySummary.temp.min, isCelsius)
            val maxTemp = TemperatureUtils.getTemperature(dailySummary.temp.max, isCelsius)
            val tempRangeText = "H:$maxTemp  L:$minTemp"
            tempRangeTV.text = tempRangeText

            //val windText = "Wind: ${dailySummary.wind_speed} mph"
            val windText = String.format(getString(R.string.wind_text), dailySummary.wind_speed.toString())
            windSpeedTV.text = windText

            val windDegree = String.format(getString(R.string.wind_degree), dailySummary.wind_deg.toString())
            windDegreeTV.text = windDegree

            var humidityText = String.format(getString(R.string.humidity_text), dailySummary.humidity.toString())
            humidityText = "$humidityText%"
            humidityTV.text = humidityText

            val sunriseTet = String.format(getString(R.string.sunrise_text), DateUtils.getHourMinutes(dailySummary.sunrise))
            sunriseTV.text = sunriseTet

            val sunsetText = String.format(getString(R.string.sunset_text), DateUtils.getHourMinutes(dailySummary.sunset))
            sunsetTV.text = sunsetText

        }
    }
}