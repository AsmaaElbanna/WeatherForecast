package com.happycomp.weatherforecast.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    val isSound: Boolean,
    val time: String,
    @PrimaryKey(autoGenerate = false)
    val timeMS: Long,
    val type: String,
    val desc: String
)