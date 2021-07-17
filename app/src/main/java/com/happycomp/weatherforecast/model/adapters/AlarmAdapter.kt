package com.happycomp.weatherforecast.model.adapters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.happycomp.weatherforecast.alarmmanager.AlarmReciever
import com.happycomp.weatherforecast.alarmmanager.AlarmService
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
            binding.switchTurnOnOff.setOnClickListener {
                if (binding.switchTurnOnOff.isChecked){
                    Toast.makeText(binding.root.context, "it's on", Toast.LENGTH_SHORT).show()

            for(value in AlarmService.listOfAlarmsIDs){
                Toast.makeText(binding.root.context, "id : $value", Toast.LENGTH_SHORT).show()

            }
            val alarmManager = binding.root.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val myIntent = Intent(binding.root.context, AlarmReciever::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                binding.root.context,AlarmService.listOfAlarmsIDs[0], myIntent, 0
            )
            alarmManager.cancel(pendingIntent)


                }else{
                    Toast.makeText(binding.root.context, "it's off", Toast.LENGTH_SHORT).show()

                }
            }
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