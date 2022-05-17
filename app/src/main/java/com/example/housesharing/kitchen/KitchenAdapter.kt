package com.example.housesharing.kitchen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.housesharing.data.Kitchen
import com.example.housesharing.databinding.KitchenViewBinding


class KitchenAdapter(var lists: List<Kitchen>, private val clickListener: OnItemClickListener): RecyclerView.Adapter<KitchenAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Kitchen?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = KitchenViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lists[position]
        holder.time.text = item.time
        holder.user.text = item.userId
        if(item.userId  == null){
            holder.user.text = "Available"
        }
        holder.kitchenCell.setOnClickListener {
            clickListener.onItemClick(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class ViewHolder(val binding: KitchenViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val time: TextView = binding.textViewKitchenTime
        val user: TextView = binding.textViewKitchenName
        val kitchenCell = binding.kitchenCell
    }

}