package com.happycomp.weatherforecast.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class Alarm(
    val isSound: Boolean,
    val time: String,
    val timeMS: Long,
    val type: String,
    val desc: String,
    val location: LatLng = LatLng(30.0595581, 31.223445),
    val address: String = "Cairo/Egypt",
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
)