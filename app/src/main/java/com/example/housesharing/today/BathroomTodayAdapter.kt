package com.example.housesharing.today

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.housesharing.R
import com.example.housesharing.data.Appointment
import com.example.housesharing.databinding.AppointmentViewBinding

class BathroomTodayAdapter(var lists: List<Appointment>): RecyclerView.Adapter<BathroomTodayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AppointmentViewBinding.inflate(layoutInflater, parent, false)
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
        }
        else{
            holder.cell.isEnabled = false
        }
        holder.color()
    }

    override fun getItemCount(): Int {
        if(lists.size > 4){
            return 4
        }
        else{
            return lists.size
        }
    }

    class ViewHolder(val binding: AppointmentViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val time: TextView = binding.textViewKitchenTime
        val user: TextView = binding.textViewKitchenName
        val cell = binding.cell


        fun color(){
            binding.cell.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.background_bathroom
                )
            )
        }



    }

}