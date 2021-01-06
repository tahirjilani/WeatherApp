package com.tahir.weatherapp.ui.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.tahir.weatherapp.R
import com.tahir.weatherapp.preference.AppPreferences
import com.tahir.weatherapp.ui.view.adapter.DailyWeatherListAdapter.DayViewHolder
import com.tahir.weatherapp.utils.AppIdlingResource
import org.hamcrest.Matchers.*
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UiInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    private var idlingResource: IdlingResource? = null

    private lateinit var appPreferences: AppPreferences

    @Before
    fun setUp() {
        idlingResource = AppIdlingResource.getInstance()
        IdlingRegistry.getInstance().register(idlingResource)

        val applicationContext = getInstrumentation().targetContext.applicationContext

        appPreferences = AppPreferences(applicationContext)
    }

    @Test
    fun clickSettings_opensMenu() {

        onView(withId(R.id.settingIV)).perform(click())
        onView(withText("Celsius")).check(matches(isDisplayed()))
    }

    @Test
    fun clickCelsius_updatesPreferences() {

        onView(withId(R.id.settingIV)).perform(click())
        onView(withText("Celsius")).perform(click())
        Assert.assertEquals(appPreferences.isCelsius(), true)
    }

    @Test
    fun clickFahrenheit_updatesPreferences() {

        onView(withId(R.id.settingIV)).perform(click())
        onView(withText("Fahrenheit")).check(matches(isDisplayed()))
        onView(withText("Fahrenheit")).perform(click())
        Assert.assertEquals(appPreferences.isCelsius(), false)
    }

    @Test
    fun clickCelsius_updatesTemperatureUI() {
        onView(withId(R.id.settingIV)).perform(click())
        onView(withText("Celsius")).perform(click())
        onView(withId(R.id.temperatureNowTV)).check(matches(isDisplayed()))
        onView(withId(R.id.temperatureNowTV)).check(matches(withText(containsString("°C" ))))
    }

    @Test
    fun clickFahrenheit_updatesTemperatureUI() {
        onView(withId(R.id.settingIV)).perform(click())
        onView(withText("Fahrenheit")).perform(click())
        onView(withId(R.id.temperatureNowTV)).check(matches(isDisplayed()))
        onView(withId(R.id.temperatureNowTV)).check(matches(withText(containsString("°F"))))
    }


    @Test
    fun changeTemperatureUnits_updatesDetailScreen() {

        onView(withId(R.id.settingIV)).perform(click())
        onView(withText("Fahrenheit")).perform(click())

        onView(withId(R.id.dailyRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DayViewHolder>(0, click())
            )
        onView(withId(R.id.temperatureTV)).check(matches(withText(containsString("°F"))))

        onView(withId(R.id.backImageView)).perform(click())

        onView(withId(R.id.settingIV)).perform(click())
        onView(withText("Celsius")).perform(click())

        onView(withId(R.id.dailyRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DayViewHolder>(0, click())
            )
        onView(withId(R.id.temperatureTV)).check(matches(withText(containsString("°C"))))

    }

    @Test
    fun scrollHourlyWeather_scrollsRecyclerView() {

        onView(withId(R.id.hourlyRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DayViewHolder>(20, scrollTo())
            )

        onView(withId(R.id.hourlyRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DayViewHolder>(0, scrollTo())
            )
    }

    @Test
    fun clickDailyWeather_opensDetailScreen() {

        onView(withId(R.id.dailyRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DayViewHolder>(0, click())
            )

        onView(withId(R.id.backImageView)).perform(click())

        onView(withId(R.id.dailyRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<DayViewHolder>(0, click())
            )
    }





    @After
    fun tearDown() {
        idlingResource?.let {
            IdlingRegistry.getInstance().unregister(it)
        }
    }


}