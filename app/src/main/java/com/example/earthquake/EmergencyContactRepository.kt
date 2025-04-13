package com.example.earthquake

import androidx.lifecycle.LiveData

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
