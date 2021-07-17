package com.happycomp.weatherforecast.view.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.happycomp.weatherforecast.alarmmanager.reciever.AlarmReciever
import com.happycomp.weatherforecast.alarmmanager.service.AlarmService
import com.happycomp.weatherforecast.databinding.ActivityAlarmBinding
import com.happycomp.weatherforecast.model.enums.AlarmType
import com.happycomp.weatherforecast.model.pojo.Alarm
import com.happycomp.weatherforecast.viewmodel.AlarmVM
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmService: AlarmService
    private lateinit var alarmReciever: AlarmReciever
    private lateinit var alarm: Alarm

    private val alarmVM: AlarmVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alarmVM = alarmVM

        alarmService = AlarmService(this)
        alarmReciever = AlarmReciever()

        alarmVM.timeInMS.value = System.currentTimeMillis() + 86400000
        alarmVM.time.value = alarmReciever.convertDate(alarmVM.timeInMS.value!!)

        var isChecking = false

        // type
        binding.rgType1.setOnCheckedChangeListener { _, checkedId ->
            val type = when (checkedId) {
                binding.rbRain.id -> AlarmType.Rain.name
                binding.rbSnow.id -> AlarmType.Snow.name
                binding.rbCloud.id -> AlarmType.Cloud.name
                else -> AlarmType.Rain.name
            }

            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.rgType2.clearCheck()
            }

            alarmVM.type.value = type
            isChecking = true
        }

        // type
        binding.rgType2.setOnCheckedChangeListener { _, checkedId ->
            val type = when (checkedId) {
                binding.rbWind.id -> AlarmType.Wind.name
                binding.rbThunder.id -> AlarmType.ThunderStorm.name
                binding.rbMistOrFogRB.id -> AlarmType.MistFog.name
                else -> AlarmType.Rain.name
            }

            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.rgType1.clearCheck()
            }
            alarmVM.type.value = type
            isChecking = true
        }

        binding.rbRain.isChecked = true

        binding.setExactAlarm.setOnClickListener {
            setAlarm { timeInMillis ->
                alarmVM.time.value = alarmReciever.convertDate(timeInMillis)
                alarmVM.timeInMS.value = timeInMillis
                binding.dateAndTime.text = alarmVM.time.value
            }
        }

        binding.btnSave.setOnClickListener {

            alarm = Alarm(
                alarmVM.isSound.get(),
                alarmVM.time.value!!,
                alarmVM.timeInMS.value!!,
                alarmVM.type.value!!,
                alarmVM.desc.get()!!
            )
            alarmVM.addAlarm(alarm)
            finish()
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
                this@AlarmActivity,
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        this@AlarmActivity,
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