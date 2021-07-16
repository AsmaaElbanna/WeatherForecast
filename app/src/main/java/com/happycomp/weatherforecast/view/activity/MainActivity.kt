package com.happycomp.weatherforecast.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.tabs.TabLayoutMediator
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ActivityMainBinding
import com.happycomp.weatherforecast.model.adapters.helpers.PagerAdapter
import com.happycomp.weatherforecast.view.fragment.AlertFragment
import com.happycomp.weatherforecast.view.fragment.FavoriteFragment
import com.happycomp.weatherforecast.view.fragment.HomeFragment
import com.happycomp.weatherforecast.view.fragment.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpdates()
    }

    private fun setUpdates() {
        val adapter = PagerAdapter(this)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(FavoriteFragment())
        adapter.addFragment(AlertFragment())
        adapter.addFragment(SettingsFragment())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()
        binding.tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        binding.tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_favorite_24)
        binding.tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_add_alert_24)
        binding.tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_settings_24)
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), REQUEST_CODE
            )
        } else {
            LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener { location ->
                if (location != null)
                    LAST_LOCATION = LatLng(location.latitude, location.longitude)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    checkPermission()
                else
                    return
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 101
        var LAST_LOCATION = LatLng(-1.0, -1.0)
    }
}