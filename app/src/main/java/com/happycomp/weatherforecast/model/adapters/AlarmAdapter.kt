package com.happycomp.weatherforecast.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.databinding.ItemAlarmBinding
import com.happycomp.weatherforecast.model.pojo.Alarm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmAdapter @Inject constructor() :
    ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(
            ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun alarmAt(position: Int): Alarm = getItem(position)

    inner class AlarmViewHolder(val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm) {
            binding.alarm = alarm
        }
    }

    companion object{
        private val diffCallback: DiffUtil.ItemCallback<Alarm> =
            object : DiffUtil.ItemCallback<Alarm>(){
                override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean = oldItem.timeMS == newItem.timeMS

                override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean = oldItem == newItem
            }
    }
}