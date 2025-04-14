package com.example.earthquake

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SafePlaceAdapater(
    private val onItemClick: (SafePlace) -> Unit,
    private val onDeleteClick: (SafePlace) -> Unit
) : ListAdapter<SafePlace, SafePlaceAdapater.SafePlaceViewHolder>(SafePlacesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SafePlaceViewHolder {
        return SafePlaceViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SafePlaceViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onItemClick, onDeleteClick)
    }

    class SafePlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewPlaceName)
        private val linkTextView: TextView = itemView.findViewById(R.id.textViewMapLink)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)
        private val openMapButton: ImageButton = itemView.findViewById(R.id.buttonOpenMap)

        fun bind(safePlace: SafePlace, onItemClick: (SafePlace) -> Unit, onDeleteClick: (SafePlace) -> Unit) {
            nameTextView.text = safePlace.name
            linkTextView.text = safePlace.mapLink
            
            itemView.setOnClickListener {
                onItemClick(safePlace)
            }
            
            deleteButton.setOnClickListener {
                onDeleteClick(safePlace)
            }
            
            openMapButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(safePlace.mapLink))
                itemView.context.startActivity(intent)
            }
        }

        companion object {
            fun create(parent: ViewGroup): SafePlaceViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_safe_place, parent, false)
                return SafePlaceViewHolder(view)
            }
        }
    }

    class SafePlacesComparator : DiffUtil.ItemCallback<SafePlace>() {
        override fun areItemsTheSame(oldItem: SafePlace, newItem: SafePlace): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SafePlace, newItem: SafePlace): Boolean {
            return oldItem == newItem
        }
    }
}
