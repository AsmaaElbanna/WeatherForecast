package com.happycomp.weatherforecast.model.pojo

import com.google.gson.annotations.SerializedName

data class Minutely (
	@SerializedName("dt") val dt : Int,
	@SerializedName("precipitation") val precipitation : Int
)