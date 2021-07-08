package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.databinding.FavoriteItemBinding
import com.happycomp.weatherforecast.model.pojo.BaseWeather

class FavoriteAdapter(private val favorites: List<BaseWeather>) :
    RecyclerView.Adapter<FavoriteAdapter.BaseWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherViewHolder {
        return BaseWeatherViewHolder(
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseWeatherViewHolder, position: Int) =
        holder.bind(favorites[position])

    override fun getItemCount() = favorites.size

    inner class BaseWeatherViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: BaseWeather) {
            binding.tvCountry.text = favorite.timezone
            binding.tvFavStatus.text = favorite.current.weather.first().description
        }
    }
}