package com.happycomp.weatherforecast.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    val isSound: Boolean,
    val time: String,
    val timeMS: Long,
    val type: String,
    val desc: String,
    @PrimaryKey(autoGenerate = false)
    var id: Int = 1,
)