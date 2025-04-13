package com.example.earthquake

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EmergencyContactsActivity : AppCompatActivity() {
    
    private lateinit var contactsAdapter: EmergencyContactAdapter
    private val contactsList = mutableListOf<EmergencyContact>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)
        
        // Handle back button click
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.contactsRecyclerView)
        contactsAdapter = EmergencyContactAdapter(contactsList)
        recyclerView.adapter = contactsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Handle add contact button
        val addButton = findViewById<FloatingActionButton>(R.id.addContactButton)
        addButton.setOnClickListener {
            // TODO: Show dialog to add new contact
            showAddContactDialog()
        }
        
        // Load sample data
        loadSampleContacts()
    }
    
    private fun loadSampleContacts() {
        // Sample data - to be replaced with database/storage implementation
        contactsList.add(EmergencyContact("Emergency Services", "911"))
        contactsList.add(EmergencyContact("Police Department", "555-0123"))
        contactsList.add(EmergencyContact("Fire Department", "555-0124"))
        contactsAdapter.notifyDataSetChanged()
    }
    
    private fun showAddContactDialog() {
        // Implementation for adding contacts will go here
        // This would typically show a dialog with fields for name and phone
    }
}
