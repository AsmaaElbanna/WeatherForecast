package com.happycomp.weatherforecast.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.model.LatLng
import com.happycomp.weatherforecast.databinding.FragmentHomeBinding
import com.happycomp.weatherforecast.model.adapters.WeatherDaysAdapter
import com.happycomp.weatherforecast.model.adapters.WeatherHoursAdapter
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.model.extra.Constants
import com.happycomp.weatherforecast.viewmodel.HomeVM
import com.happycomp.weatherforecast.viewmodel.HomeVMFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), NetworkHandler {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var assistedFactory: HomeVMFactory

    private val homeVM: HomeVM by viewModels {
        HomeVMFactory.Factory(assistedFactory, this)
    }

    @SuppressLint("MissingPermission")
    private val resultLocationPermission =
        registerForActivityResult(RequestMultiplePermissions()) { result ->
            if (result.containsValue(true)) {
                val locationManager =
                    requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, (1000 * 60 * 10).toLong(), 500F
                    ) {
                        homeVM.getWeather(LatLng(it.latitude, it.longitude))
                    }
                } else {
                    buildAlertMessageNoGps()
                    homeVM.getWeather()
                }
            }
        }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val alertDialog =
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton(
                    "No"
                ) { dialog, _ -> dialog.cancel() }.create()

        alertDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            }
        }, 3000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val weatherHoursAdapter = WeatherHoursAdapter()
        val weatherDaysAdapter = WeatherDaysAdapter()
        binding.rvHoursWeather.adapter = weatherHoursAdapter
        binding.rvDaysWeather.adapter = weatherDaysAdapter

        homeVM.loadLastResult(requireContext()).also {
            if (it != null) {
                binding.weather = it
                binding.userUnits = Constants.userUnits
                weatherHoursAdapter.setData(it.hourly!!)
                weatherDaysAdapter.setData(it.daily!!)
                homeVM.lastKnownLocation = LatLng(it.lat, it.lon)
            }
        }

        homeVM.weatherData.observe(viewLifecycleOwner, {
            binding.weather = it
            weatherHoursAdapter.setData(it.hourly!!)
            weatherDaysAdapter.setData(it.daily!!)
            homeVM.saveAsLastResult(requireContext(), it)
            binding.userUnits = Constants.userUnits
        })

        Constants.currentUnits.observe(viewLifecycleOwner, {
            if (homeVM.weatherData.value != null) {
                homeVM.getWeather()
            }
        })

        checkPermission()
        return binding.root
    }

    private fun checkPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        resultLocationPermission.launch(permissions)
    }

    override fun onConnectionFailed() {
        super.onConnectionFailed()
        Toast.makeText(
            requireContext(),
            "Failed to Connect, Please Check Your Network!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showIndicator() {
        binding.spinKit.visibility = View.VISIBLE
    }

    override fun hideIndicator() {
        binding.spinKit.visibility = View.INVISIBLE
    }

    override fun onErrorOccurred() {
        super.onErrorOccurred()
        Toast.makeText(requireContext(), "Error Occurred, Please try Again!", Toast.LENGTH_SHORT)
            .show()
    }
}