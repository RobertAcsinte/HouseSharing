package com.example.housesharing.house

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.housesharing.R
import com.example.housesharing.data.House
import com.example.housesharing.data.Note
import com.example.housesharing.databinding.MembersViewBinding

class MembersAdapter(var lists: List<String>): RecyclerView.Adapter<MembersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = MembersViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lists[position]
        holder.name.text = item
        if(position % 2 != 0){
            holder.colorOdd()
        }
        else{
            holder.colorEven()
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    class ViewHolder(val binding: MembersViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.textViewMemberName


        fun colorEven(){
            binding.cell.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.color_member_list_even
                )
            )
        }

        fun colorOdd(){
            binding.cell.setCardBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.color_member_list_odd
                )
            )
        }
    }

}