package com.example.earthquake.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.earthquake.data.SafePlace

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
