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
import com.example.housesharing.databinding.FragmentAccountBinding
import com.example.housesharing.databinding.FragmentLoggedInBinding
import com.example.housesharing.viewmodel.AccountViewModel
import com.example.housesharing.viewmodel.LoggedInViewModel


class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_account,
            container,
            false
        )

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        accountViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.textViewAccountEmail.text = user.email
            }
        })

        //if user is logged out, go to register/login fragment
        accountViewModel.loggedOutMutableLiveData.observe(viewLifecycleOwner, Observer { loggedOut ->
            if (loggedOut ) {
                view?.findNavController()?.navigate(R.id.action_accountFragment_to_loginRegisterFragment)
            }
        })

        logoutButton()

        return binding.root
    }

    private fun logoutButton(){
        binding.buttonAccountLogOut.setOnClickListener { accountViewModel.logOut() }
    }


}