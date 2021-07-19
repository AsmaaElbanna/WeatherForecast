package com.happycomp.weatherforecast.view.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.happycomp.weatherforecast.alarmmanager.AlarmReciever
import com.happycomp.weatherforecast.alarmmanager.AlarmService
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

    private var lastID = 0

    private val resultContractMap =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                if (intent != null) {
                    val location = intent.getParcelableExtra<LatLng>(MapsActivity.SELECTED_LOCATION)
                    val address = intent.getStringExtra(MapsActivity.SELECTED_ADDRESS)
                    if (location != null && address != null) {
                        alarmVM.location = location
                        alarmVM.address = address
                        binding.tvSelectedLocatioon.text = address
                    } else {
                        Toast.makeText(this, "Wrong Location", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alarmVM = alarmVM

        alarmVM.alarms.observe(this, {
            if (it.isNotEmpty())
                lastID = it.last().id
        })

        alarmService = AlarmService(applicationContext)
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

        binding.btnLocation.setOnClickListener {
            resultContractMap.launch(Intent(this, MapsActivity::class.java))
        }

        binding.btnSave.setOnClickListener {

            alarm = Alarm(
                alarmVM.isSound.get(),
                alarmVM.time.value!!,
                alarmVM.timeInMS.value!!,
                alarmVM.type.value!!,
                alarmVM.desc.get()!!,
                alarmVM.location,
                alarmVM.address,
                ++lastID
            )

            alarmVM.addAlarm(alarm)

            setAlarm { timeInMillis ->
                alarmVM.time.value = alarmReciever.convertDate(timeInMillis)
                alarmVM.timeInMS.value = timeInMillis
            }

            alarmService.setExactAlarm(alarmVM.timeInMS.value!!, alarm.id)

            finish()
        }
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