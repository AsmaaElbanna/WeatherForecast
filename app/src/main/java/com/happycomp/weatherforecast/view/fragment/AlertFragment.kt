package com.happycomp.weatherforecast.view.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.happycomp.weatherforecast.alarmmanager.AlarmReciever
import com.happycomp.weatherforecast.databinding.FragmentAlertBinding
import com.happycomp.weatherforecast.model.adapters.AlarmAdapter
import com.happycomp.weatherforecast.model.adapters.helpers.SwipeToDelete
import com.happycomp.weatherforecast.model.interfaces.SwipeListener
import com.happycomp.weatherforecast.model.pojo.Alarm
import com.happycomp.weatherforecast.view.activity.AlarmActivity
import com.happycomp.weatherforecast.viewmodel.AlarmVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlertFragment : Fragment(), SwipeListener {
    private lateinit var binding: FragmentAlertBinding

    private val alarmVM: AlarmVM by viewModels()

    @Inject
    lateinit var alarmAdapter: AlarmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertBinding.inflate(inflater, container, false)
        binding.rvAlarms.adapter = alarmAdapter
        ItemTouchHelper(SwipeToDelete(this)).attachToRecyclerView(binding.rvAlarms)

        binding.setAlarm.setOnClickListener {
            val intent = Intent(activity, AlarmActivity::class.java)
            startActivity(intent)
        }

        alarmVM.alarms.observe(viewLifecycleOwner, {
            alarmAdapter.submitList(it as ArrayList<Alarm>)
        })

        return binding.root
    }

    override fun onItemSwipeToDelete(position: Int) {
        val alarm = alarmAdapter.alarmAt(position)
        val appContext = requireActivity().applicationContext

        alarmVM.deleteAlarm(alarm)

        val myIntent = Intent(appContext, AlarmReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(appContext, alarm.id, myIntent, 0)
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}