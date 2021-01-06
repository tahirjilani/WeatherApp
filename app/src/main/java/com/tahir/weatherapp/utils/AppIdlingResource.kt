package com.tahir.weatherapp.utils

import androidx.test.espresso.idling.CountingIdlingResource
import java.lang.Exception

/**
 * When adding idling resources into your app, Android highly recommend placing the idling resource
 * logic in the app itself and performing only the registration and unregistration operations in
 * your tests.
 */
class AppIdlingResource {
    companion object {
        private var countingIdlingResource: CountingIdlingResource? = null

        /**
         * Only call this from your tests
         */
        fun getInstance(): CountingIdlingResource {
            if (countingIdlingResource == null){
                countingIdlingResource = CountingIdlingResource("app_idle_resource")
            }
            return countingIdlingResource!!
        }

        fun increment() {
            countingIdlingResource?.increment()
        }

        fun decrement() {
            try {
                countingIdlingResource?.decrement()
            }catch (ex:Exception){}
        }
    }
}