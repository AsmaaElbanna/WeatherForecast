package com.happycomp.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.happycomp.weatherforecast.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpdates()
    }

    private fun setUpdates(){
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(FavoriteFragment())
        adapter.addFragment(AlertFragment())
        adapter.addFragment(SettingsFragment())
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_favorite_24)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_add_alert_24)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_settings_24)

    }
}