package com.example.housesharing.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        (activity as AppCompatActivity).supportActionBar?.hide()

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        registerButton()

        return binding.root
    }

    private fun registerButton(){
        binding.buttonRegister.setOnClickListener {
            var email: String = binding.editTextTextEmailAddressRegister.text.toString()
            var firstName: String = binding.editTextTextFirstNameRegister.text.toString()
            var lastName: String = binding.editTextTextLastNameRegister.text.toString()
            var password: String = binding.editTextTextPasswordRegister.text.toString()
            var repeatPassword: String = binding.editTextTextPasswordRepeatRegister.text.toString()

            if(email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()){
                if(password == repeatPassword){
                    registerViewModel.register(email, firstName, lastName, password).observe(viewLifecycleOwner){
                        if(it.exception == null){
                            view?.findNavController()?.navigate(R.id.action_registerFragment_to_loadFragment)
                        }
                        else{
                            Toast.makeText(context, it.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(context, "The passwords don't match!",
                        Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context, "Please fill out all the fields!",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }

}