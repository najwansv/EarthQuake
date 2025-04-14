package com.example.earthquake.repository

import androidx.lifecycle.LiveData
import com.example.earthquake.data.SafePlace
import com.example.earthquake.dao.SafePlaceDao

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
