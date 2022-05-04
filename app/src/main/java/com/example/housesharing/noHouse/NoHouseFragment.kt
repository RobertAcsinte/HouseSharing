package com.example.housesharing.noHouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentCreateHouseBinding
import com.example.housesharing.databinding.FragmentNoHouseBinding


class NoHouseFragment : Fragment() {

    private lateinit var binding: FragmentNoHouseBinding

    private lateinit var viewModel: NoHouseViewModel
    private lateinit var viewModelFactory: NoHouseViewModelFactory

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

        viewModelFactory = NoHouseViewModelFactory(NoHouseFragmentArgs.fromBundle(requireArguments()).account)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NoHouseViewModel::class.java)
        binding.textViewNoHouseName.text = getString(R.string.hello) + " " + viewModel.nameLive.value.toString()

        return binding.root
    }

}