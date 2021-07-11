package com.happycomp.weatherforecast.util

import java.util.concurrent.atomic.AtomicInteger

object RandomInUtil {
    private val seed = AtomicInteger()

    fun getRandomInt():Int = seed.getAndIncrement()+System.currentTimeMillis().toInt()
}