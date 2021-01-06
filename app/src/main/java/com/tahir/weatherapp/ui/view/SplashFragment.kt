package com.tahir.weatherapp.ui.view

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tahir.weatherapp.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var handler: Handler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(requireContext().mainLooper)
        handler.postDelayed(runnable, 1500L)
    }

    private var runnable = Runnable {
        val action = SplashFragmentDirections.actionSplashFragmentToWeatherListFragment()
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }


}