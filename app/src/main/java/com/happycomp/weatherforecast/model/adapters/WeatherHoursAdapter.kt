package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ItemWeatherHoursBinding
import com.happycomp.weatherforecast.model.pojo.Hourly
import com.happycomp.weatherforecast.model.extra.Constants
import com.squareup.picasso.Picasso

class WeatherHoursAdapter : RecyclerView.Adapter<WeatherHoursAdapter.ViewHolder>() {
    private var hoursList: List<Hourly> = listOf()

    fun setData(hoursList: List<Hourly>) {
        this.hoursList = hoursList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemWeatherHoursBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(hoursList[position])

    override fun getItemCount(): Int = hoursList.size

    inner class ViewHolder(val binding: ItemWeatherHoursBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Hourly) {
            binding.weatherHour = currentItem
            binding.userUnits = Constants.userUnits
            val icon = currentItem.weather.first().icon
            val uri = "http://openweathermap.org/img/wn/$icon@2x.png"
            Picasso.get().load(uri).placeholder(R.drawable.clouds).error(R.drawable.clouds)
                .into(binding.imageHour)
        }
    }
}