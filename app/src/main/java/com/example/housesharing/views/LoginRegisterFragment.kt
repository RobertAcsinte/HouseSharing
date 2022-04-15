package com.example.housesharing.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentLoginRegisterBinding
import com.example.housesharing.viewmodel.LoginRegisterViewModel


class LoginRegisterFragment : Fragment() {


    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    private lateinit var binding: FragmentLoginRegisterBinding

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
            R.layout.fragment_login_register,
            container,
            false
        )

        loginRegisterViewModel = ViewModelProvider(this).get(LoginRegisterViewModel::class.java)
        loginRegisterViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show()
            }
        })

        registerButton()
        return binding.root
    }

    private fun registerButton(){
        binding.buttonRegister.setOnClickListener {
            var email: String = binding.editTextTextEmailAddress.text.toString()
            var password: String = binding.editTextTextPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                loginRegisterViewModel.register(email, password)
            }
        }
    }

}