package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.databinding.FavoriteItemBinding
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteAdapter @Inject constructor() :
    ListAdapter<BaseWeather, FavoriteAdapter.BaseWeatherViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherViewHolder =
        BaseWeatherViewHolder(
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BaseWeatherViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun favoriteAt(position: Int): BaseWeather = getItem(position)

    inner class BaseWeatherViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: BaseWeather) {
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