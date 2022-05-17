package com.example.housesharing.kitchen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.housesharing.R
import com.example.housesharing.data.CalendarDateModel
import com.example.housesharing.databinding.CalendarViewBinding


class CalendarAdapter(private var list: List<CalendarDateModel>, private val clickListener: OnItemClickListener): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var selectedItemPosition: Int = 0

    interface OnItemClickListener {
        fun onItemClick(item: CalendarDateModel?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CalendarViewBinding.inflate(layoutInflater, parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = list[position]
        holder.day.text = item.calendarDay
        holder.date.text = item.calendarDate
        if(selectedItemPosition == position){
            holder.colorSelected()
        }
        else{
            holder.colorNotSelected()
        }
        holder.dateCell.setOnClickListener {
            selectedItemPosition = position
            clickListener.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class CalendarViewHolder(val binding: CalendarViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val day: TextView = binding.textViewKitchenCalendarDay
        val date: TextView = binding.textViewKitchenCalendarDate
        val dateCell = binding.dateCell

        fun colorSelected(){
            binding.dateCell.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.background_button_week_selected
                )
            )
        }

        fun colorNotSelected(){
            binding.dateCell.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.background_button_week
                )
            )
        }
    }


}