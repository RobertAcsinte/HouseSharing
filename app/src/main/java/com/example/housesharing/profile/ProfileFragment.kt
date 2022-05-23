package com.example.housesharing.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*


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

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarNotes)
        (activity as AppCompatActivity).title = "My Account"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)


        //if user is logged out, go to register/login fragment
        profileViewModel.loggedOutMutableLiveData.observe(viewLifecycleOwner, Observer { logged ->
            if (logged) {
                view?.findNavController()?.navigate(R.id.action_accountFragment_to_loginFragment)
            }
        })

        fetchData()
        logoutButton()

        firstNameEdit()
        lastNameEdit()

        return binding.root
    }

    private fun fetchData(){
        profileViewModel.fetch()
        profileViewModel.accountInfoMutableLiveData.observe(viewLifecycleOwner){
            binding.textViewProfileFirstName.text = it.firstName
            binding.textViewProfileLastName.text = it.lastName
            binding.textViewProfileEmail.text = it.email
        }
    }

    private fun logoutButton(){
        binding.buttonAccountLogOut.setOnClickListener { profileViewModel.logOut() }
    }

    private fun firstNameEdit(){
        binding.buttonEditProfileFirstName.setOnClickListener {
            val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_profile, null)
            var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setView(customDialogLayout)
                .setTitle("First Name")
                .setPositiveButton("Save"){dialog, which ->
                }
                .setNegativeButton("Cancel"){dialog, which ->

                }
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValue)
                if(editText.text.isEmpty()){
                    Toast.makeText(context, "Please complete the field!", Toast.LENGTH_SHORT).show()
                }
                else{
                    profileViewModel.updateFirstName(editText.text.toString())
                    dialog.dismiss()
                }
            }
        }
    }

    private fun lastNameEdit(){
        binding.buttonEditProfileLastName.setOnClickListener {
            val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_profile, null)
            var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setView(customDialogLayout)
                .setTitle("Last Name")
                .setPositiveButton("Save"){dialog, which ->
                }
                .setNegativeButton("Cancel"){dialog, which ->

                }
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValue)
                if(editText.text.isEmpty()){
                    Toast.makeText(context, "Please complete the field!", Toast.LENGTH_SHORT).show()
                }
                else{
                    profileViewModel.updateLastName(editText.text.toString())
                    dialog.dismiss()
                }
            }
        }
    }



}