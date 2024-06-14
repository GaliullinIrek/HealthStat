package com.example.snoozestats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sleep(
    var date: String,
    var time: Double,
    var emoji: Int = 3,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
)
