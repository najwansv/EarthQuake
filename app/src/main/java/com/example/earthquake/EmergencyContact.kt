package com.example.earthquake

data class EmergencyContact(
    val name: String,
    val phoneNumber: String,
    val id: Long = System.currentTimeMillis() // Simple unique ID for demonstration
)
