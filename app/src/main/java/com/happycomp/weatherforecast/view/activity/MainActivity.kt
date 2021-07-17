package com.happycomp.weatherforecast.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ActivityMainBinding
import com.happycomp.weatherforecast.model.adapters.helpers.PagerAdapter
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.util.Constants
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
        Constants.currentUnits.value = Units.valueOf(Constants.getSavedUnit(this))
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
        binding.viewPager.isUserInputEnabled = false

    }
}