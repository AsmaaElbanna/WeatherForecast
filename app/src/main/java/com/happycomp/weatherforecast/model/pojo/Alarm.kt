package com.happycomp.weatherforecast.model.pojo

data class Alarm(
    var isSound: Boolean,
    var time: String,
    var type: String,
    var desc: String
)