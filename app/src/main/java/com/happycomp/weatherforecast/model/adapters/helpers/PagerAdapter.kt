package com.happycomp.weatherforecast.model.adapters.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Singleton

@Singleton
class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val mFragmentList = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) = mFragmentList.add(fragment)

    override fun getItemCount(): Int = mFragmentList.size

    override fun createFragment(position: Int): Fragment = mFragmentList[position]
}