package com.example.housesharing.notes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.housesharing.data.Note
import com.example.housesharing.databinding.NotesViewBinding

class NotesAdapter(var lists: List<Note>, private val clickListener: OnItemClickListener): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Note?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = NotesViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lists[position]
        holder.title.text = item.title
//        if(item.title  == "Some title"){
//            holder.title.setTextColor(Color.RED)
//        }
        holder.content.text = item.content
        holder.noteCell.setOnClickListener {
            clickListener.onItemClick(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class ViewHolder(val binding: NotesViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.textViewNoteTitle
        val content: TextView = binding.textViewNoteContent
        val noteCell = binding.noteCell
    }

}
