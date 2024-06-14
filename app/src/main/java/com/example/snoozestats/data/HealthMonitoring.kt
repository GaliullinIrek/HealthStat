package com.example.snoozestats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HealthMonitoring(
    var date: String,
    val normanWater: Double,
    val realWater: Double,
    val height: Int,
    val weight: Double,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
