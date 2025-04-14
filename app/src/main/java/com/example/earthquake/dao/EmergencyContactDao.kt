package com.example.earthquake.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.earthquake.data.EmergencyContact

@Dao
interface EmergencyContactDao {
    @Query("SELECT * FROM emergency_contacts ORDER BY name ASC")
    fun getAllContacts(): LiveData<List<EmergencyContact>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: EmergencyContact)
    
    @Delete
    suspend fun delete(contact: EmergencyContact)
    
    @Update
    suspend fun update(contact: EmergencyContact)
    
    @Query("DELETE FROM emergency_contacts")
    suspend fun deleteAll()
}
