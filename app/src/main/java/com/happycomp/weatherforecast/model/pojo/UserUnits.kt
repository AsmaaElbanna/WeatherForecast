package com.happycomp.weatherforecast.model.pojo

data class UserUnits(
    val tempUnit: String,
    val speedUnits: String,
    val humidity: String = "%",
    val pressure: String = "hpa",
    val clouds: String = "%"
)