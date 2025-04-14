package com.example.earthquake.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergency_contacts")
data class EmergencyContact(
    val name: String,
    val phoneNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
