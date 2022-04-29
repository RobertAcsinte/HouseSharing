package com.example.housesharing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentCreateHouseBinding
import com.example.housesharing.databinding.FragmentNoHouseBinding


class NoHouseFragment : Fragment() {

    private lateinit var binding: FragmentNoHouseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_no_house,
            container,
            false
        )

        return binding.root
    }

}