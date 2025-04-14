package com.example.earthquake.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.earthquake.data.SafePlace
import com.example.earthquake.dao.SafePlaceDao

@Database(entities = [SafePlace::class], version = 1, exportSchema = false)
abstract class SafePlaceDatabase : RoomDatabase() {
    abstract fun safePlaceDao(): SafePlaceDao
    
    companion object {
        @Volatile
        private var INSTANCE: SafePlaceDatabase? = null
        
        fun getDatabase(context: Context): SafePlaceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SafePlaceDatabase::class.java,
                    "safe_place_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
