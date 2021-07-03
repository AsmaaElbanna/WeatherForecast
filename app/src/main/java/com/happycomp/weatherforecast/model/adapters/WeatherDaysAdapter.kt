package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.model.pojo.Daily
import com.happycomp.weatherforecast.model.pojo.Hourly

class WeatherDaysAdapter(private var dailyList: List<Hourly>) : RecyclerView.Adapter<WeatherDaysAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_weather_day,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dailyList[position]
        holder.tvTemp.text = currentItem.temp.toString()


    }

    override fun getItemCount(): Int {
     return dailyList.size
    }

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
         var tvDay:TextView
         var tvDate :TextView
         var img : ImageView
         var tvStatus : TextView
         var tvTemp : TextView
         var tv : TextView

        init {
            tvDay = itemView.findViewById(R.id.day_text_view)
            tvDate = itemView.findViewById(R.id.date_text_view)
            img = itemView.findViewById(R.id.imageView)
            tvStatus = itemView.findViewById(R.id.status_text_view)
            tvTemp = itemView.findViewById(R.id.temp_text_view)
            tv = itemView.findViewById(R.id.textView18)
        }

    }
}