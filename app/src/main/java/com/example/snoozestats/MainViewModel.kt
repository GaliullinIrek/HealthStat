package com.example.snoozestats

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.snoozestats.data.HealthMonitoring
import com.example.snoozestats.data.MainDatabase
import com.example.snoozestats.data.Sleep
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(val database: MainDatabase): ViewModel() {
    val lastSevenHealthsItems = database.dao.getLastSevenHealthMonitoring()
    val lastSevenItems = database.dao.getLastSevenSleeps()

    val startSleeping = mutableStateOf("")
    val endSleeping = mutableStateOf("")
    val sleepQuality = mutableStateOf(0)
    val resultSleep = mutableStateOf("")
    val sleepDouble = mutableDoubleStateOf(0.0)

    val realWater = mutableStateOf("")
    val height = mutableStateOf("")
    val weight = mutableStateOf("")

    val normalWater = if (weight.value.isNotEmpty()) 30.0*weight.value.toDouble() else 40.0

    var openDialog = mutableStateOf(false)


    val currentDate = LocalDate.now()
    val date = "${currentDate.dayOfMonth}/${currentDate.monthValue}"

    fun insetItems() = viewModelScope.launch {
        val newItem = Sleep(date, sleepDouble.value, sleepQuality.value)
        database.dao.insertItem(newItem)
    }

    fun insertHealthItem() = viewModelScope.launch {
        val newHealthItem = HealthMonitoring(date, normalWater, realWater.value.toDouble(), height.value.toInt(), weight.value.toDouble())
        database.dao.insertHealthItem(newHealthItem)
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = checkNotNull((extras[APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}