package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.model.pojo.Daily
import java.sql.Date
import java.sql.Timestamp

class WeatherDaysAdapter(private var dailyList: List<Daily>) : RecyclerView.Adapter<WeatherDaysAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_weather_day,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dailyList[position]
        // view day
        val stamp = Timestamp(currentItem.dt.toLong()*1000)
        val date = Date(stamp.getTime())
        holder.tvDay.text=date.toString()
        //view temp
        holder.tvTemp.text = currentItem.temp.day.toString()
        //view min max temp
        holder.tvMinMaxTemp.text = currentItem.temp.min.toString()+"/"+currentItem.temp.max.toString()
        // view UVI
        holder.tvUVI.text = "uvi: "+currentItem.uvi.toString()
        // view description
        holder.tvStatus.text = currentItem.weather[0].description

    }

    override fun getItemCount(): Int {
     return dailyList.size
    }


    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
         var tvDay:TextView
         var tvMinMaxTemp :TextView
         var img : ImageView
         var tvStatus : TextView
         var tvTemp : TextView
         var tvUVI : TextView

        init {
            tvDay = itemView.findViewById(R.id.day_text_view)
            tvMinMaxTemp = itemView.findViewById(R.id.min_max_text_view)
            img = itemView.findViewById(R.id.imageView)
            tvStatus = itemView.findViewById(R.id.status_text_view)
            tvTemp = itemView.findViewById(R.id.temp_text_view)
            tvUVI = itemView.findViewById(R.id.tvUVI)
        }

    }
}