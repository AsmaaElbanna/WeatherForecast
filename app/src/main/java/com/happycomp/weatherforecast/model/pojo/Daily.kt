package com.happycomp.weatherforecast.model.pojo

import com.google.gson.annotations.SerializedName

data class Daily (
    @SerializedName("dt") val dt : Int,
    @SerializedName("temp") val temp : Float

    )
