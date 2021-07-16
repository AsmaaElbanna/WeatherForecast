package com.happycomp.weatherforecast.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.bottomsheet.TemperatureUnit
import com.happycomp.weatherforecast.bottomsheet.WindUnit
import com.happycomp.weatherforecast.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    lateinit var binding : FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tempUnitContainer.isClickable
        binding.tempUnitContainer.setOnClickListener {
            TemperatureUnit().show(requireActivity().supportFragmentManager,"Temp unit")
        }
        binding.windUnitContainer.setOnClickListener {
            WindUnit().show(requireActivity().supportFragmentManager,"Temp unit")
        }
    }



}