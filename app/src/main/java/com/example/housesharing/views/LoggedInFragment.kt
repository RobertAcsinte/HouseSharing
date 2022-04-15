package com.example.housesharing.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentLoggedInBinding
import com.example.housesharing.databinding.FragmentLoginRegisterBinding
import com.example.housesharing.viewmodel.LoggedInViewModel
import com.example.housesharing.viewmodel.LoginRegisterViewModel


class LoggedInFragment : Fragment() {

    private lateinit var loggedInViewModel: LoggedInViewModel

    private lateinit var binding: FragmentLoggedInBinding

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

        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        loggedInViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.textViewEmail.text = user.email
            }
        })

        loggedInViewModel.loggedOutMutableLiveData.observe(viewLifecycleOwner, Observer { loggedOut ->
            if (loggedOut ) {
                view?.findNavController()?.navigate(R.id.action_loggedInFragment_to_loginRegisterFragment)
            }
        })

        binding.buttonLogOut.setOnClickListener { loggedInViewModel.logOut() }

        return binding.root
    }
}