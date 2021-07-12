package com.happycomp.weatherforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.happycomp.weatherforecast.databinding.FragmentHomeBinding
import com.happycomp.weatherforecast.model.adapters.WeatherDaysAdapter
import com.happycomp.weatherforecast.model.adapters.WeatherHoursAdapter
import com.happycomp.weatherforecast.viewmodel.HomeVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeVM: HomeVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        if (homeVM.weatherData.value == null)
            homeVM.getWeather()
        homeVM.weatherData.observe(viewLifecycleOwner, {
            binding.weather = it

            binding.rvHoursWeather.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvHoursWeather.adapter = WeatherHoursAdapter(it.hourly!!)

            binding.rvDaysWeather.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvDaysWeather.adapter = WeatherDaysAdapter(it.daily!!)
        })
        return binding.root
    }
}