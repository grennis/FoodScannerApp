package com.innodroid.foodscannerapp.services.scans

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScansDao {
    @Query("SELECT * from scans ORDER BY date DESC")
    fun getScans(): LiveData<List<ScanStored>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(product: ScanStored)

    @Query("DELETE FROM scans where upc in (:upcs)")
    suspend fun delete(upcs: List<String>)
}
