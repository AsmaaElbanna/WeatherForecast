package com.happycomp.weatherforecast.model.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.happycomp.weatherforecast.extra.getOrAwaitValue
import com.happycomp.weatherforecast.model.pojo.Alarm
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlarmDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: WeatherDataBase
    private lateinit var alarmDao: AlarmDao

    private val fakeDataList = arrayListOf<Alarm>(
        Alarm(
            true,
            "23/9/2021 , 03:59:00",
            18227896,
            "Rain",
            "My trip"
        ),
        Alarm(
            false,
            "23/10/2021 , 04:59:00",
            182997896,
            "Cloud",
            "My trip"
        ),
        Alarm(
            false,
            "23/3/2021 , 04:59:00",
            182997896,
            "Cloud",
            "My trip"
        )
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()

        alarmDao = database.alarmDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAlarm() = runBlocking {

        alarmDao.addAlarm(fakeDataList.first())

        val allAlarms = alarmDao.observeAllAlarms().getOrAwaitValue()
        assertThat(allAlarms.contains(fakeDataList.first()))
    }

    @Test
    fun deleteAlarmItem() = runBlocking {
        alarmDao.addAlarm(fakeDataList[1])
        alarmDao.deleteAlarm(fakeDataList[1])
        val allAlarms = alarmDao.observeAllAlarms().getOrAwaitValue()
        assertThat(allAlarms).doesNotContain(fakeDataList[1])

    }

    @Test
    fun removePastAlarms() = runBlocking {
        alarmDao.addAlarm(fakeDataList.last())
        alarmDao.deleteOldAlarms()
        val allAlarms = alarmDao.observeAllAlarms().getOrAwaitValue()
        assertThat(allAlarms).doesNotContain(fakeDataList.last())
    }


}