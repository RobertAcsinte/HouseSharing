package com.example.housesharing.house

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentHouseBinding
import com.example.housesharing.databinding.FragmentKitchenBinding
import com.example.housesharing.profile.ProfileViewModel
import com.example.housesharing.today.NotesTodayAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class HouseFragment : Fragment() {

    private lateinit var binding: FragmentHouseBinding

    private lateinit var houseViewModel: HouseViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_house,
            container,
            false
        )

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarNotes)
        (activity as AppCompatActivity).title = "My House"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        houseViewModel = ViewModelProvider(this).get(HouseViewModel::class.java)

        houseViewModel.houseInfoMutableLiveData.observe(viewLifecycleOwner){
            //list members
            val manager = LinearLayoutManager(activity)
            val adapter = MembersAdapter(it.members)
            binding.recyclerViewListMembers.layoutManager = manager
            binding.recyclerViewListMembers.adapter = adapter

            //house name and id
            binding.textViewHouseNameMyHouse.text = it.name.toString()
            binding.textViewHouseIdMyHouse.text = "(# " + it.id.toString() + ")"
        }

        houseViewModel.dataHouse()

        return binding.root
    }


}