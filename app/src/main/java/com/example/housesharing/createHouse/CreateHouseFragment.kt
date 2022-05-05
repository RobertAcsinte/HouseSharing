package com.example.housesharing.createHouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentCreateHouseBinding

class CreateHouseFragment : Fragment() {

    private lateinit var binding: FragmentCreateHouseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_house,
            container,
            false
        )

        return binding.root
    }


}