package com.happycomp.weatherforecast.model.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

public class PagerAdapter(supportFragmentManager:FragmentManager) : FragmentPagerAdapter(supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList =ArrayList<String>()



    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
//      when(position){
//          0 -> {return HomeFragment()
//          }
//          1 -> {return FavoriteFragment()
//          }
//          2 -> {return SettingsFragment()
//          }
//          3 -> {return AlertFragment()
//          }
//          else -> {return HomeFragment()
//          }
//      } //end when

    }
    override fun getCount(): Int {
        return mFragmentList.size
    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        return mFragmentTitleList[position]
//    }

    fun addFragment (fragment:Fragment){
        mFragmentList.add(fragment)
       // mFragmentTitleList.add(title)
    }


//    fun addFragment (fragment:Fragment,title:String){
//        mFragmentList.add(fragment)
//        // mFragmentTitleList.add(title)
//    }





}