package com.example.earthquake

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SafePlaceDao {
    @Query("SELECT * FROM safe_places ORDER BY name ASC")
    fun getAllSafePlaces(): LiveData<List<SafePlace>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(safePlace: SafePlace)
    
    @Update
    suspend fun update(safePlace: SafePlace)
    
    @Delete
    suspend fun delete(safePlace: SafePlace)
}
