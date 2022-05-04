package com.example.housesharing.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.viewModel.AccountViewModel


class LoadFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        accountViewModel.accountData().observe(viewLifecycleOwner) {
            if(it.account?.houseId == "null"){
                val action = LoadFragmentDirections.
                    actionLoadFragmentToNoHouseFragment(it.account!!)
                view?.findNavController()?.navigate(action)
            }
        }

        return inflater.inflate(R.layout.fragment_load, container, false)
    }

}