package com.example.earthquake

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmergencyContactViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: EmergencyContactRepository
    val allContacts: LiveData<List<EmergencyContact>>
    
    init {
        val contactDao = EmergencyContactDatabase.getDatabase(application, viewModelScope).emergencyContactDao()
        repository = EmergencyContactRepository(contactDao)
        allContacts = repository.allContacts
    }
    
    fun insert(contact: EmergencyContact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }
    
    fun delete(contact: EmergencyContact) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contact)
    }
    
    fun update(contact: EmergencyContact) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(contact)
    }
}
