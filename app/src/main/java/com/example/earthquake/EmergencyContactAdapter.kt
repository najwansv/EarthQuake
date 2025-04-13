package com.example.earthquake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageButton
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView

class EmergencyContactAdapter(
    private var contacts: List<EmergencyContact>
) : RecyclerView.Adapter<EmergencyContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.contactNameTextView)
        val phoneTextView: TextView = view.findViewById(R.id.contactPhoneTextView)
        val callButton: ImageButton = view.findViewById(R.id.callButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.emergency_contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phoneNumber
        
        holder.callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${contact.phoneNumber}")
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    fun updateContacts(newContacts: List<EmergencyContact>) {
        this.contacts = newContacts
        notifyDataSetChanged()
    }

    fun getContactAtPosition(position: Int): EmergencyContact {
        return contacts[position]
    }

    override fun getItemCount() = contacts.size
}
