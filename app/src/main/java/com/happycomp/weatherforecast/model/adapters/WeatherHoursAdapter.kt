package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.model.pojo.Hourly
import java.sql.Date
import java.sql.Timestamp

class WeatherHoursAdapter(private var hoursList: List<Hourly>) : RecyclerView.Adapter<WeatherHoursAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_hours,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentItem =hoursList[position]
        //view temp
        holder.tvTemp.text = currentItem.temp.toString()

        // view date
        val stamp = Timestamp(currentItem.dt.toLong()*1000)
        val date = Date(stamp.getTime())
        holder.tvHour.text = date.toString()



    }

    override fun getItemCount(): Int {
        return hoursList.size

    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img:ImageView
        var tvHour:TextView
        var tvTemp:TextView
         init {
             img = itemView.findViewById(R.id.image_hour)
             tvHour = itemView.findViewById(R.id.hour_text_view)
             tvTemp = itemView.findViewById(R.id.temp_text_view)
         }

    }

}