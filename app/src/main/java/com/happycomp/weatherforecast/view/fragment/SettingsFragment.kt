package com.happycomp.weatherforecast.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.happycomp.weatherforecast.bottomsheet.TemperatureUnit
import com.happycomp.weatherforecast.bottomsheet.WindUnit
import com.happycomp.weatherforecast.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tempUnitContainer.setOnClickListener {
            TemperatureUnit().show(requireActivity().supportFragmentManager, "Temp unit")
        }


    }
}