package com.happycomp.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.happycomp.weatherforecast.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setUpdates()
    }
    fun setUpdates(){
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(),"Home")
        adapter.addFragment(FavoriteFragment(),"Favorite")
        adapter.addFragment(AlertFragment(),"Alert")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_favorite_24)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_add_alert_24)





    }
}