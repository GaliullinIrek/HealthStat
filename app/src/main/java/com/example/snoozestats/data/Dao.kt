package com.example.snoozestats.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertItem(sleep: Sleep)

    @Insert
    suspend fun insertHealthItem(healthMonitoring: HealthMonitoring)

    @Query("SELECT * FROM sleep ORDER BY id DESC LIMIT 7")
    fun getLastSevenSleeps(): Flow<List<Sleep>>

    @Query("SELECT * FROM healthmonitoring ORDER BY id DESC LIMIT 7")
    fun getLastSevenHealthMonitoring(): Flow<List<HealthMonitoring>>

    @Query("DELETE FROM sleep")
    fun deleteInfoTable()

    @Query("DELETE FROM healthmonitoring")
    fun deleteHealthMonitoringTable()
}
