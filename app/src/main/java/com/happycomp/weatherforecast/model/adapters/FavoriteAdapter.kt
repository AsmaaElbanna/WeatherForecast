 package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.databinding.FavoriteItemBinding
import com.happycomp.weatherforecast.model.pojo.BaseWeather

class FavoriteAdapter :
    ListAdapter<BaseWeather, FavoriteAdapter.BaseWeatherViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherViewHolder {
        return BaseWeatherViewHolder(
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseWeatherViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun favoriteAt(position: Int): BaseWeather{
        return getItem(position)
    }

    inner class BaseWeatherViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: BaseWeather) {
            binding.tvCountry.text = favorite.timezone
            binding.tvFavStatus.text = favorite.current.weather.first().description
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