package com.happycomp.weatherforecast.model.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ItemWeatherDayBinding
import com.happycomp.weatherforecast.databinding.ItemWeatherHoursBinding
import com.happycomp.weatherforecast.model.pojo.Daily
import com.happycomp.weatherforecast.model.pojo.Hourly
import com.squareup.picasso.Picasso
import java.sql.Date
import java.sql.Timestamp

class WeatherDaysAdapter : RecyclerView.Adapter<WeatherDaysAdapter.ViewHolder>() {

    private var dailyList: List<Daily> = listOf()
    fun setData(dailyList: List<Daily>) {
        this.dailyList = dailyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemWeatherDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dailyList[position])
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }


    inner class ViewHolder(val binding : ItemWeatherDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Daily) {
            binding.weatherDay = currentItem
            // view image
            val icon = currentItem.weather.first().icon
            val uri = "http://openweathermap.org/img/wn/$icon@2x.png"
            Picasso.get().load(uri).placeholder(R.drawable.bakar).error(R.drawable.bakar)
                .into(binding.imageView)
        }
    }
}