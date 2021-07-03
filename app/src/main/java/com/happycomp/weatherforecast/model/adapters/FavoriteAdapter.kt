package com.happycomp.weatherforecast.model.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.view.fragment.FavoriteFragment

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyHolder>(){

    private val country = arrayOf("Egypt","Palestine","Iraq")
    private val temp = arrayOf("20°","30°","40°")
    private val status = arrayOf("snow","cloudy","rain")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        holder.countryText.text = country[position]
        holder.tempText.text = temp[position]
        holder.statusText.text = status[position]
    }

    override fun getItemCount(): Int {
        return country.size
    }

    inner class MyHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        var countryText: TextView
        var tempText: TextView
        var statusText: TextView

        init{
            countryText=itemView.findViewById(R.id.country_fav)
            tempText=itemView.findViewById(R.id.temp_fav)
            statusText=itemView.findViewById(R.id.status_fav)

            itemView.setOnClickListener{
                var position: Int =getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, FavoriteFragment::class.java).apply {
                    putExtra("NUMBER", position)
                    putExtra("CODE", countryText.text)
                    putExtra("CATEGORY", tempText.text)
                    putExtra("CONTENT", statusText.text)
                }
                context.startActivity(intent)
            }
        }

    }

}