package com.happycomp.weatherforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.happycomp.weatherforecast.databinding.FragmentHomeBinding
import com.happycomp.weatherforecast.model.adapters.WeatherDaysAdapter
import com.happycomp.weatherforecast.model.adapters.WeatherHoursAdapter
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

        if (homeVM.weatherData.value == null)
            homeVM.getWeather()
        homeVM.weatherData.observe(viewLifecycleOwner, {
            binding.tvLocation.text = it.timezone
            binding.tvTemp.text = it.current.temp.toString()
            binding.tvStatus.text = it.current.weather.first().description
            binding.tvCloud.text = it.current.clouds.toString()
            binding.tvHumidity.text = it.current.humidity.toString()
            binding.tvPressure.text = it.current.pressure.toString()
            binding.tvWind.text = it.current.wind_speed.toString()

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