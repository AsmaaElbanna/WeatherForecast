package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ItemWeatherDayBinding
import com.happycomp.weatherforecast.model.pojo.Daily
import com.happycomp.weatherforecast.util.Constants
import com.squareup.picasso.Picasso

class WeatherDaysAdapter : RecyclerView.Adapter<WeatherDaysAdapter.ViewHolder>() {
    private var dailyList: List<Daily> = listOf()

    fun setData(dailyList: List<Daily>) {
        this.dailyList = dailyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ItemWeatherDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int =dailyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(dailyList[position])

    inner class ViewHolder(val binding: ItemWeatherDayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Daily){
            binding.weatherDay = currentItem
            binding.userUnits = Constants.userUnits

            val icon = currentItem.weather.first().icon
            val uri = "http://openweathermap.org/img/wn/$icon@2x.png"
            Picasso.get().load(uri).placeholder(R.drawable.bakar).error(R.drawable.bakar)
                .into(binding.imageView)
        }
    }
}