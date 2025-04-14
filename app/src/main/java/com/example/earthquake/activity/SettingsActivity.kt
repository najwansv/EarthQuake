package com.example.earthquake.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.earthquake.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        // Handle back button click
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Handle emergency contacts card click
        val emergencyContactsCard = findViewById<CardView>(R.id.emergencyContactsCard)
        emergencyContactsCard.setOnClickListener {
            val intent = Intent(this, EmergencyContactsActivity::class.java)
            startActivity(intent)
        }

        // Handle safe places card click
        val safePlacesCard = findViewById<CardView>(R.id.safePlacesCard)
        safePlacesCard.setOnClickListener {
            val intent = Intent(this, SafePlaceActivity::class.java)
            startActivity(intent)
        }
    }
}
