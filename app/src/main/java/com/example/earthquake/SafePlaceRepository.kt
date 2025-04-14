package com.example.earthquake

import androidx.lifecycle.LiveData

class SafePlaceRepository(private val safePlaceDao: SafePlaceDao) {
    val allSafePlaces: LiveData<List<SafePlace>> = safePlaceDao.getAllSafePlaces()
    
    suspend fun insert(safePlace: SafePlace) {
        safePlaceDao.insert(safePlace)
    }
    
    suspend fun update(safePlace: SafePlace) {
        safePlaceDao.update(safePlace)
    }
    
    suspend fun delete(safePlace: SafePlace) {
        safePlaceDao.delete(safePlace)
    }
}
