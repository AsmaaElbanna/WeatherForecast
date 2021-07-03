package com.happycomp.weatherforecast.model.enums

import com.google.gson.annotations.SerializedName

enum class Exclude {
    @SerializedName("hourly") Hourly,
    @SerializedName("minutely") Minutely
}