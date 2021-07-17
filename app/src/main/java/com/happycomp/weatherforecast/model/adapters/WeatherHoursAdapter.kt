package com.happycomp.weatherforecast.model.adapters

import android.os.Build
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ItemWeatherHoursBinding
import com.happycomp.weatherforecast.model.pojo.Hourly
import com.squareup.picasso.Picasso


class WeatherHoursAdapter : RecyclerView.Adapter<WeatherHoursAdapter.ViewHolder>() {

    private lateinit var binding: ItemWeatherHoursBinding
    private var hoursList: List<Hourly> = listOf()
    fun setData(hoursList: List<Hourly>) {
        this.hoursList = hoursList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemWeatherHoursBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = hoursList[position]
        binding.weatherHour = currentItem
        // view date
        holder.tvHour.text = DateFormat.format("hh:mm A", currentItem.dt.toLong() * 1000)
        // view image
        val icon = currentItem.weather.first().icon
        val uri = "http://openweathermap.org/img/wn/$icon@2x.png"
        Picasso.get().load(uri).placeholder(R.drawable.bakar).error(R.drawable.bakar)
            .into(holder.img)

    }

    override fun getItemCount(): Int {
        return hoursList.size

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.image_hour)
        var tvHour: TextView = itemView.findViewById(R.id.hour_text_view)

    }

}