package com.happycomp.weatherforecast.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.happycomp.weatherforecast.databinding.FragmentAlertBinding
import com.happycomp.weatherforecast.model.adapters.AlarmAdapter
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
        alarmVM.deleteAlarm(alarmAdapter.alarmAt(position))
    }
}