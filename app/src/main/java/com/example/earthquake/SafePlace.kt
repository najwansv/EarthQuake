package com.example.earthquake

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "safe_places")
data class SafePlace(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val mapLink: String
)
