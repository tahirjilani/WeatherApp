package com.tahir.weatherapp.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.tahir.weatherapp.R

abstract class LocationAwareFragment constructor(private val layoutId: Int) : Fragment(layoutId) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val latitude = 24.466667
    private val longitude = 54.366669
    //val timeZone = "Asia/Dubai"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            requireActivity()
        )
        getWeatherForCurrentLocation()
    }

    protected fun getWeatherForCurrentLocation(){
        if (locationEnabled()){
            requestUserLocationWithPermission()
        }else{
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(getString(R.string.location_instructions))
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }

            builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    private fun locationEnabled(): Boolean {
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestUserLocationWithPermission(){

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                getLocation()
            }
            else -> {
                askLocationPermissions()
            }
        }
    }

    private fun askLocationPermissions(){
        requestPermissionLauncher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLocation()
        } else {

            Toast.makeText(requireContext(),
                getString(R.string.default_location_note), Toast.LENGTH_SHORT).show()

            onLocationFound(latitude, longitude)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){

        val locationRequest = LocationRequest.create()?.apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            //smallestDisplacement = 1000.0f //meters
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            if (locationResult.locations.isNotEmpty()){

                stopLocationUpdates()

                val selectedLocation = locationResult.locations[0]
                onLocationFound(selectedLocation.latitude, selectedLocation.longitude)
            }
        }
    }

    override fun onDestroyView() {
        stopLocationUpdates()
        super.onDestroyView()
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    abstract fun onLocationFound(lat: Double, lon: Double)
}