package com.happycomp.weatherforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.happycomp.weatherforecast.databinding.FragmentHomeBinding
import com.happycomp.weatherforecast.viewmodel.HomeVM

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeVM: HomeVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeVM = ViewModelProvider(requireActivity()).get(HomeVM::class.java)

        if(homeVM.weatherData.value == null)
            homeVM.getWeather()

        homeVM.weatherData.observe(viewLifecycleOwner, { weatherData ->
            binding.tvLocation.text = weatherData.timezone
        })

        return binding.root
    }
}