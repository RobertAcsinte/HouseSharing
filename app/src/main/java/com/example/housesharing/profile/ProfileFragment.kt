package com.example.housesharing.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


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
        setHasOptionsMenu(true)

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
        changeEmail()
        changePassword()

        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
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
        binding.buttonAccountLogOut.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setTitle("Confirm logging out?")
                .setPositiveButton("Yes"){dialog, which ->
                    profileViewModel.logOut()
                }
                .setNegativeButton("No"){dialog, which ->
                }
                .show()
        }
    }

    private fun firstNameEdit(){
        binding.buttonEditProfileFirstName.setOnClickListener {
            val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_profile, null)
            val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValue)
            editText.setText(profileViewModel.accountInfoMutableLiveData.value!!.firstName)
            var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setView(customDialogLayout)
                .setTitle("First Name")
                .setPositiveButton("Save"){dialog, which ->
                }
                .setNegativeButton("Cancel"){dialog, which ->
                }
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

                if(editText.text.isEmpty()){
                    Toast.makeText(context, "Please complete the field!", Toast.LENGTH_SHORT).show()
                }
                else{
                    profileViewModel.updateFirstName(editText.text.toString().replaceFirstChar { it.uppercase() })
                    dialog.dismiss()
                }
            }
        }
    }

    private fun lastNameEdit(){
        binding.buttonEditProfileLastName.setOnClickListener {
            val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_profile, null)
            val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValue)
            editText.setText(profileViewModel.accountInfoMutableLiveData.value!!.lastName)
            var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setView(customDialogLayout)
                .setTitle("Last Name")
                .setPositiveButton("Save"){dialog, which ->
                }
                .setNegativeButton("Cancel"){dialog, which ->
                }
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if(editText.text.isEmpty()){
                    Toast.makeText(context, "Please complete the field!", Toast.LENGTH_SHORT).show()
                }
                else{
                    profileViewModel.updateLastName(editText.text.toString().replaceFirstChar { it.uppercase() })
                    dialog.dismiss()
                }
            }
        }
    }


    private fun changeEmail(){
        binding.buttonEditProfileEmail.setOnClickListener {
            val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_auth_password, null)
            var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setView(customDialogLayout)
                .setTitle("Change your email")
                .setMessage("Your password is needed for this action")
                .setPositiveButton("Done"){dialog, which ->
                }
                .setNegativeButton("Cancel"){dialog, which ->

                }
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValuePassword)
                if(editText.text.isEmpty()){
                    Toast.makeText(context, "Please complete the field!", Toast.LENGTH_SHORT).show()
                }
                else{
                    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
                    val user = firebaseAuth.currentUser
                    val credential = EmailAuthProvider
                        .getCredential(profileViewModel.accountInfoMutableLiveData.value?.email!!, editText.text.toString())
                    user!!.reauthenticate(credential)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                dialog.dismiss()
                                emailEditDb()
                            }
                            else{
                                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    private fun emailEditDb(){
        val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_profile, null)
        val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValue)
        editText.setText(profileViewModel.accountInfoMutableLiveData.value!!.email)
        var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setView(customDialogLayout)
            .setTitle("Email")
            .setPositiveButton("Save"){dialog, which ->
            }
            .setNegativeButton("Cancel"){dialog, which ->

            }
            .show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if(editText.text.isEmpty()){
                Toast.makeText(context, "Please complete the field!", Toast.LENGTH_SHORT).show()
            }
            else{
                profileViewModel.updateEmail(editText.text.toString()).observe(viewLifecycleOwner){
                    if(it == null){
                        dialog.dismiss()
                    }
                    else{
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun changePassword(){
        binding.buttonEditProfilePassword.setOnClickListener {
            val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_auth_password, null)
            var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setView(customDialogLayout)
                .setTitle("Change your password")
                .setMessage("Your password is needed for this action")
                .setPositiveButton("Done"){dialog, which ->
                }
                .setNegativeButton("Cancel"){dialog, which ->

                }
                .show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValuePassword)
                if(editText.text.isEmpty()){
                    Toast.makeText(context, "Please complete the field!", Toast.LENGTH_SHORT).show()
                }
                else{
                    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
                    val user = firebaseAuth.currentUser
                    val credential = EmailAuthProvider
                        .getCredential(profileViewModel.accountInfoMutableLiveData.value?.email!!, editText.text.toString())
                    user!!.reauthenticate(credential)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                dialog.dismiss()
                                passwordEditDb()
                            }
                            else{
                                Toast.makeText(context, it.exception!!.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    private fun passwordEditDb(){
        val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_change_password, null)
        var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setView(customDialogLayout)
            .setTitle("Reset your password")
            .setPositiveButton("Save"){dialog, which ->
            }
            .setNegativeButton("Cancel"){dialog, which ->

            }
            .show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val editTextPassword = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValuePasswordReset)
            val editTextPasswordRepeat = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValuePasswordResetRepeat)
            if(editTextPassword.text.isEmpty() || editTextPasswordRepeat.text.isEmpty()){
                Toast.makeText(context, "Please complete all the fields!", Toast.LENGTH_SHORT).show()
            }
            else{
                if(editTextPassword.text.toString() != editTextPasswordRepeat.text.toString()){
                    Toast.makeText(context, "Passwords don't match!", Toast.LENGTH_SHORT).show()
                }
                else{
                    profileViewModel.updatePassword(editTextPassword.text.toString()).observe(viewLifecycleOwner){
                        if(it == null){
                            dialog.dismiss()
                        }
                        else{
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}