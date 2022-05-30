package com.example.housesharing.house

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentHouseBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HouseFragment : Fragment() {

    private lateinit var binding: FragmentHouseBinding

    private lateinit var houseViewModel: HouseViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_house,
            container,
            false
        )

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarNotes)
        (activity as AppCompatActivity).title = "My House"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)




        houseViewModel = ViewModelProvider(this).get(HouseViewModel::class.java)

        houseViewModel.houseInfoMutableLiveData.observe(viewLifecycleOwner){
            //list members
            val manager = LinearLayoutManager(activity)
            val adapter = MembersAdapter(it.members)
            binding.recyclerViewListMembers.layoutManager = manager
            binding.recyclerViewListMembers.adapter = adapter

            //house name and id
            binding.textViewHouseNameMyHouse.text = it.name.toString()
            binding.textViewHouseIdMyHouse.text = it.id.toString()
        }

        houseViewModel.dataHouse()
        changeName()
        leaveHouse()

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

    private fun changeName(){
        binding.buttonEditHouseName.setOnClickListener {
            val customDialogLayout: View = layoutInflater.inflate(R.layout.dialog_profile, null)
            val editText = customDialogLayout.findViewById<EditText>(R.id.editTextDialogValue)
            editText.setText(houseViewModel.houseInfoMutableLiveData.value!!.name)
            var dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setView(customDialogLayout)
                .setTitle("House Name")
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
                    houseViewModel.changeName(editText.text.toString())
                    dialog.dismiss()
                }
            }
        }
    }

    private fun leaveHouse(){
        binding.buttonLeaveHouse.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setTitle("Do you confirm leaving the house?")
                .setPositiveButton("Yes"){dialog, which ->
                    houseViewModel.leaveHouse().observe(viewLifecycleOwner){
                        view?.findNavController()?.navigate(R.id.action_houseFragment_to_loadFragment)
                    }
                }
                .setNegativeButton("No"){dialog, which ->
                }
                .show()
        }
    }

}