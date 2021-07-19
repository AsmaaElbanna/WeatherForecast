package com.happycomp.weatherforecast.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.happycomp.weatherforecast.model.enums.AlarmType
import com.happycomp.weatherforecast.model.pojo.Alarm
import com.happycomp.weatherforecast.model.room.data.AlarmDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmVM @Inject constructor(private val alarmDao: AlarmDao): ViewModel() {

    var isSound = ObservableBoolean()
    var type = MutableLiveData<String>().also { it.value = AlarmType.Rain.name }
    var time: MutableLiveData<String> = MutableLiveData()
    var timeInMS: MutableLiveData<Long> = MutableLiveData()
    var desc: ObservableField<String> = ObservableField<String>().also { it.set("") }
    var location: LatLng = LatLng(30.0595581, 31.223445)
    var address: String = "Cairo/Egypt"

    val alarms: LiveData<List<Alarm>> = alarmDao.observeAllAlarms()

    fun addAlarm(alarm: Alarm){
        viewModelScope.launch {
            alarmDao.addAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: Alarm){
        viewModelScope.launch {
            alarmDao.deleteAlarm(alarm)
        }
    }
}