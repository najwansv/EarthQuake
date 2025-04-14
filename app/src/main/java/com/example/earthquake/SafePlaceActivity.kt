package com.example.earthquake

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SafePlaceActivity : AppCompatActivity() {
    private lateinit var safePlaceViewModel: SafePlaceViewModel
    private lateinit var adapter: SafePlaceAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safe_place)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSafePlaces)
        adapter = SafePlaceAdapater(
            onItemClick = { safePlace -> showEditDialog(safePlace) },
            onDeleteClick = { safePlace -> showDeleteConfirmationDialog(safePlace) }
        )
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        safePlaceViewModel = ViewModelProvider(this).get(SafePlaceViewModel::class.java)
        safePlaceViewModel.allSafePlaces.observe(this) { safePlaces ->
            adapter.submitList(safePlaces)
        }

        val fab = findViewById<FloatingActionButton>(R.id.fabAddSafePlace)
        fab.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_safe_place, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextPlaceName)
        val linkEditText = dialogView.findViewById<EditText>(R.id.editTextMapLink)

        AlertDialog.Builder(this)
            .setTitle("Add Safe Place")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameEditText.text.toString().trim()
                val link = linkEditText.text.toString().trim()
                
                if (name.isNotEmpty() && link.isNotEmpty()) {
                    val safePlace = SafePlace(name = name, mapLink = link)
                    safePlaceViewModel.insert(safePlace)
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(safePlace: SafePlace) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_safe_place, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editTextPlaceName)
        val linkEditText = dialogView.findViewById<EditText>(R.id.editTextMapLink)
        
        nameEditText.setText(safePlace.name)
        linkEditText.setText(safePlace.mapLink)

        AlertDialog.Builder(this)
            .setTitle("Edit Safe Place")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val name = nameEditText.text.toString().trim()
                val link = linkEditText.text.toString().trim()
                
                if (name.isNotEmpty() && link.isNotEmpty()) {
                    val updatedSafePlace = SafePlace(id = safePlace.id, name = name, mapLink = link)
                    safePlaceViewModel.update(updatedSafePlace)
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmationDialog(safePlace: SafePlace) {
        AlertDialog.Builder(this)
            .setTitle("Delete Safe Place")
            .setMessage("Are you sure you want to delete ${safePlace.name}?")
            .setPositiveButton("Delete") { _, _ ->
                safePlaceViewModel.delete(safePlace)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
