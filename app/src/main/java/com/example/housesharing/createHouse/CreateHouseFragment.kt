package com.example.housesharing.createHouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentCreateHouseBinding
import com.example.housesharing.login.LoginViewModel
import com.example.housesharing.noHouse.NoHouseViewModel

class CreateHouseFragment : Fragment() {

    private lateinit var binding: FragmentCreateHouseBinding

    private lateinit var viewModel: CreateHouseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_create_house,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(CreateHouseViewModel::class.java)

        createHouseButton()
        return binding.root
    }

    private fun createHouseButton(){
        binding.buttonCreateHouse.setOnClickListener {
            var name: String = binding.editTextTextHouseNameCreateHouse.text.toString()
            if(name.isNotEmpty()){
                viewModel.createHouse(name).observe(viewLifecycleOwner){
                    if(it.exception != null){
                        Toast.makeText(context, it.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                    else{
                        view?.findNavController()?.navigate(R.id.action_createHouseFragment_to_todayFragment)
                    }
                }
            }
            else{
                Toast.makeText(context, "Please fill out the field!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


}