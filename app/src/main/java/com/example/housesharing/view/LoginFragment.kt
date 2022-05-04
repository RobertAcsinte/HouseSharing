package com.example.housesharing.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentLoginRegisterBinding
import com.example.housesharing.viewModel.AccountViewModel


class LoginFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    private lateinit var binding: FragmentLoginRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login_register,
            container,
            false
        )
        (activity as AppCompatActivity).supportActionBar?.hide()


        //Go to LoggedInFragment if the user is already logged in
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        accountViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_loadFragment)
            }
        })

        registerButton()
        loginButton()
        return binding.root
    }

    private fun registerButton(){
        binding.buttonRegister.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun loginButton(){
        binding.buttonLogin.setOnClickListener {
            var email: String = binding.editTextTextEmailAddressLogin.text.toString()
            var password: String = binding.editTextTextPasswordLogin.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                accountViewModel.login(email, password).observe(viewLifecycleOwner){
                    if(it.exception != null){
                        Toast.makeText(context, it.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(context, "Please fill out all the fields!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}