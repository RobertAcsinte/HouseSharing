package com.example.housesharing.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentLoggedInBinding
import com.example.housesharing.databinding.FragmentLoginRegisterBinding


class LoggedInFragment : Fragment() {

    private lateinit var binding: FragmentLoggedInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_logged_in,
            container,
            false
        )

        return binding.root
    }
}