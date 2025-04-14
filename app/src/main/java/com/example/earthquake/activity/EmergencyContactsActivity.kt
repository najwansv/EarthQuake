package com.example.earthquake.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.earthquake.data.EmergencyContact
import com.example.earthquake.adapter.EmergencyContactAdapter
import com.example.earthquake.viewmodel.EmergencyContactViewModel
import com.example.earthquake.R
import com.google.android.material.textfield.TextInputEditText

class EmergencyContactsActivity : AppCompatActivity() {
    
    private lateinit var contactsAdapter: EmergencyContactAdapter
    private lateinit var contactViewModel: EmergencyContactViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)
        
        // Initialize ViewModel
        contactViewModel = ViewModelProvider(this).get(EmergencyContactViewModel::class.java)
        
        // Handle back button click
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed()
        }
        
        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.contactsRecyclerView)
        contactsAdapter = EmergencyContactAdapter(emptyList())
        recyclerView.adapter = contactsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Observe the LiveData from ViewModel
        contactViewModel.allContacts.observe(this, Observer { contacts ->
            contacts?.let { 
                contactsAdapter.updateContacts(it)
            }
        })
        
        // Handle add contact button
        val addButton = findViewById<FloatingActionButton>(R.id.addContactButton)
        addButton.setOnClickListener {
            showAddContactDialog()
        }
    }
    
    private fun showAddContactDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null)
        
        val nameEditText = dialogView.findViewById<TextInputEditText>(R.id.nameEditText)
        val phoneEditText = dialogView.findViewById<TextInputEditText>(R.id.phoneEditText)
        
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameEditText.text.toString()
                val phone = phoneEditText.text.toString()
                
                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    val newContact = EmergencyContact(name, phone)
                    contactViewModel.insert(newContact)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        
        dialog.show()
    }
}
