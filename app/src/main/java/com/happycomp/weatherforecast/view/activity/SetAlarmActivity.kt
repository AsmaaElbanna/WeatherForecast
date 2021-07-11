package com.happycomp.weatherforecast.view.activity

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.databinding.ActivitySetAlarmBinding
import com.happycomp.weatherforecast.model.pojo.Alarm
import com.happycomp.weatherforecast.reciever.AlarmReciever
import com.happycomp.weatherforecast.service.AlarmService
import com.happycomp.weatherforecast.util.RandomInUtil
import java.util.*


class SetAlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetAlarmBinding
    lateinit var alarmService: AlarmService
    lateinit var alarmReciever: AlarmReciever
    private lateinit var alarm: Alarm

    private var isSound: Boolean = true
    private var time: String = " "
    private var type: String = " "
    private var desc: String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmService = AlarmService(this)
        alarmReciever = AlarmReciever()

        // set Date and time
        binding.setExactAlarm.setOnClickListener {
            setAlarm { timeInMillis ->
                alarmService.setExactAlarm(timeInMillis)
                time = alarmReciever.convertDate(timeInMillis)
                Toast.makeText(this, "" + time, Toast.LENGTH_SHORT).show()
            }

        }
        // alarm sound
        binding.rgAlarmSound.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbNotification.id -> {
                    isSound = false
                    Toast.makeText(this, "Type : " + isSound, Toast.LENGTH_SHORT).show()
                }
                binding.rbAlarm.id -> {
                    isSound = true
                    Toast.makeText(this, "Type : " + isSound, Toast.LENGTH_SHORT).show()
                }

            }
        }

        // type
        binding.rgType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbRain.id -> {
                    type = "Rain"
                    Toast.makeText(this, "Type : " + type, Toast.LENGTH_SHORT).show()
                }
                binding.rbSnow.id -> {
                    type = "Snow"
                    Toast.makeText(this, "Type : " + type, Toast.LENGTH_SHORT).show()
                }
                binding.rbCloud.id -> {
                    type = "Cloud"
                    Toast.makeText(this, "Type : " + type, Toast.LENGTH_SHORT).show()
                }
                binding.rbWind.id -> {
                    type = "Wind"
                    Toast.makeText(this, "Type : " + type, Toast.LENGTH_SHORT).show()
                }
                binding.rbThunder.id -> {
                    type = "Thunder Storm"
                    Toast.makeText(this, "Type : " + type, Toast.LENGTH_SHORT).show()
                }
                binding.rbMistOrFogRB.id -> {
                    type = "Mist/Fog"
                    Toast.makeText(this, "Type : " + type, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // set description
      desc = binding.descET.text.toString()

        binding.btnSave.setOnClickListener{
            alarm = Alarm(isSound,time,type,desc)
            Toast.makeText(this, "data : " + isSound+time+type+desc , Toast.LENGTH_SHORT).show()
        }

        // alarm=Alarm()
//        binding.setRepetitive.setOnClickListener{
//            setAlarm { alarmService.setRepetitiveAlarm(it) }
//        }
//
//        binding.cancelAlarm.setOnClickListener{
//            for(value in AlarmService.listOfAlarmsIDs){
//                Toast.makeText(this, "id : $value", Toast.LENGTH_SHORT).show()
//
//            }
//            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val myIntent = Intent(applicationContext, AlarmReciever::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(
//                applicationContext,AlarmService.listOfAlarmsIDs[0], myIntent, 0
//            )
//            alarmManager.cancel(pendingIntent)
//        }
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                this@SetAlarmActivity,
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        this@SetAlarmActivity,
                        0,
                        { _, hour, min ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, min)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

}