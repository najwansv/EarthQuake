package com.example.earthquake

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [EmergencyContact::class], version = 1, exportSchema = false)
abstract class EmergencyContactDatabase : RoomDatabase() {
    
    abstract fun emergencyContactDao(): EmergencyContactDao
    
    companion object {
        @Volatile
        private var INSTANCE: EmergencyContactDatabase? = null
        
        fun getDatabase(context: Context, scope: CoroutineScope): EmergencyContactDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmergencyContactDatabase::class.java,
                    "emergency_contact_database"
                )
                .addCallback(EmergencyContactDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class EmergencyContactDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.emergencyContactDao())
                    }
                }
            }
        }
        
        suspend fun populateDatabase(contactDao: EmergencyContactDao) {
            // Delete all content
            contactDao.deleteAll()
            
            // Add sample contacts
            contactDao.insert(EmergencyContact("Emergency Services", "911"))
            contactDao.insert(EmergencyContact("Police Department", "555-0123"))
            contactDao.insert(EmergencyContact("Fire Department", "555-0124"))
        }
    }
}
