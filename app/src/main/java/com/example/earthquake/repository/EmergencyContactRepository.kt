package com.example.earthquake.repository

import androidx.lifecycle.LiveData
import com.example.earthquake.data.EmergencyContact
import com.example.earthquake.dao.EmergencyContactDao

class EmergencyContactRepository(private val emergencyContactDao: EmergencyContactDao) {
    
    val allContacts: LiveData<List<EmergencyContact>> = emergencyContactDao.getAllContacts()
    
    suspend fun insert(contact: EmergencyContact) {
        emergencyContactDao.insert(contact)
    }
    
    suspend fun delete(contact: EmergencyContact) {
        emergencyContactDao.delete(contact)
    }
    
    suspend fun update(contact: EmergencyContact) {
        emergencyContactDao.update(contact)
    }
}
