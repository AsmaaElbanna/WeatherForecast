package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ItemFavoriteBinding
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteAdapter @Inject constructor() :
    ListAdapter<BaseWeather, FavoriteAdapter.BaseWeatherViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherViewHolder =
        BaseWeatherViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BaseWeatherViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun favoriteAt(position: Int): BaseWeather = getItem(position)

    inner class BaseWeatherViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: BaseWeather) {
            val icon = favorite.current.weather.first().icon
            val uri = "http://openweathermap.org/img/wn/$icon@2x.png"
            Picasso.get().load(uri).placeholder(R.drawable.bakar).error(R.drawable.bakar)
                .into(binding.imgStatus)
            binding.favorite = favorite
        }
    }

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<BaseWeather> =
            object : DiffUtil.ItemCallback<BaseWeather>() {
                override fun areItemsTheSame(oldItem: BaseWeather, newItem: BaseWeather): Boolean {
                    return oldItem.timezone == newItem.timezone
                }

                override fun areContentsTheSame(
                    oldItem: BaseWeather,
                    newItem: BaseWeather
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}