package com.example.housesharing.profile

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
import com.example.housesharing.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.textViewAccountEmail.text = user.email
            }
        })

        //if user is logged out, go to register/login fragment
        profileViewModel.loggedOutMutableLiveData.observe(viewLifecycleOwner, Observer { logged ->
            if (logged) {
                view?.findNavController()?.navigate(R.id.action_accountFragment_to_loginFragment)
            }
        })

        logoutButton()

        return binding.root
    }

    private fun logoutButton(){
        binding.buttonAccountLogOut.setOnClickListener { profileViewModel.logOut() }
    }


}