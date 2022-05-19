package com.example.housesharing.kitchen

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.housesharing.R
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


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lists[position]
        if(item.timeStartHour!! < 10){
            if(item.timeStartMinute == 0){
                holder.time.text = "0" + item.timeStartHour.toString() + ":" + item.timeStartMinute.toString() + "0" + " - " + "0" + item.timeEndHour.toString() + ":" + item.timeEndMinute.toString()
            }
            else{
                if(item.timeEndHour != 10){
                    holder.time.text = "0" + item.timeStartHour.toString() + ":" + item.timeStartMinute.toString() + " - " + "0" + item.timeEndHour.toString() + ":" + item.timeEndMinute.toString() + "0"
                }
                else{
                    holder.time.text = "0" + item.timeStartHour.toString() + ":" + item.timeStartMinute.toString() + " - " + item.timeEndHour.toString() + ":" + item.timeEndMinute.toString() + "0"
                }
            }
        }
        else{
            if(item.timeStartMinute == 0){
                holder.time.text = item.timeStartHour.toString() + ":" + item.timeStartMinute.toString() + "0" + " - " + item.timeEndHour.toString() + ":" + item.timeEndMinute.toString()
            }
            else{
                holder.time.text = item.timeStartHour.toString() + ":" + item.timeStartMinute.toString() + " - " + item.timeEndHour.toString() + ":" + item.timeEndMinute.toString() + "0"
            }
        }
        holder.user.text = item.firstName.toString() + " " + item.lastName.toString()
        if(item.userId  == null){
            holder.user.text = "Available"
            holder.colorAvailable()
        }
        else{
            holder.cell.isEnabled = false
            holder.colorNotAvailable()
        }
        holder.cell.setOnClickListener {
            clickListener.onItemClick(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class ViewHolder(val binding: KitchenViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val time: TextView = binding.textViewKitchenTime
        val user: TextView = binding.textViewKitchenName
        val cell = binding.cell


        fun colorAvailable(){
            binding.cell.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.background_kitchen
                )
            )
        }

        fun colorNotAvailable(){
            binding.cell.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.background_kitchen_reserved
                )
            )
        }

    }

}