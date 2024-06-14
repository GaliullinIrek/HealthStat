package com.example.snoozestats.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Sleep::class,
        HealthMonitoring::class
    ],
    version = 1,
)
abstract class MainDatabase: RoomDatabase() {
    abstract val dao: Dao
    companion object {
        fun createDatabase(context: Context):MainDatabase {
            return Room.databaseBuilder(
                context,
                MainDatabase::class.java,
                "main.db"
            ).build()
        }
    }
}