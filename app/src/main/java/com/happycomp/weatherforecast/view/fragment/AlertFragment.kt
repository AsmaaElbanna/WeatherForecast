package com.happycomp.weatherforecast.view.fragment

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.happycomp.weatherforecast.alarmmanager.ReminderBroadCast
import com.happycomp.weatherforecast.databinding.FragmentAlertBinding
import com.happycomp.weatherforecast.service.AlarmService
import com.happycomp.weatherforecast.view.activity.SetAlarmActivity
import java.util.*

class AlertFragment : Fragment() {
    private lateinit var binding: FragmentAlertBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertBinding.inflate(inflater, container, false)
//       // binding.button.setOnClickListener(View.OnClickListener {
//            Toast.makeText(context, "Reminder set", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, ReminderBroadCast::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(context,
//                0, intent, 0)
//           val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val timeAtButtonClicked = System.currentTimeMillis()
//            val thirtySecInMillis = (1000 * 10).toLong()
//
//            alarmManager[AlarmManager.RTC_WAKEUP, timeAtButtonClicked + thirtySecInMillis] =
//                pendingIntent
//        })

       binding.setAlarm.setOnClickListener{
           val intent =Intent(activity,SetAlarmActivity::class.java)
           startActivity(intent)
       }

        return binding.root
    }



}