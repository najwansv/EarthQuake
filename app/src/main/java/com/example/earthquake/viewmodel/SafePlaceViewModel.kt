package com.example.earthquake.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.earthquake.data.SafePlace
import com.example.earthquake.repository.SafePlaceRepository
import com.example.earthquake.database.SafePlaceDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SafePlaceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SafePlaceRepository
    val allSafePlaces: LiveData<List<SafePlace>>
    
    init {
        val safePlaceDao = SafePlaceDatabase.getDatabase(application).safePlaceDao()
        repository = SafePlaceRepository(safePlaceDao)
        allSafePlaces = repository.allSafePlaces
    }
    
    fun insert(safePlace: SafePlace) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(safePlace)
    }
    
    fun update(safePlace: SafePlace) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(safePlace)
    }
    
    fun delete(safePlace: SafePlace) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(safePlace)
    }
}
