package com.example.housesharing.load

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R


class LoadFragment : Fragment() {

    private lateinit var loadViewModel: LoadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loadViewModel = ViewModelProvider(this).get(LoadViewModel::class.java)
        loadViewModel.accountData().observe(viewLifecycleOwner) {
            if(it.account?.houseId == "null"){
                val action = LoadFragmentDirections.
                    actionLoadFragmentToNoHouseFragment(it.account!!)
                view?.findNavController()?.navigate(action)
            }
            else{
                view?.findNavController()?.navigate(R.id.action_loadFragment_to_todayFragment)
            }
        }

        return inflater.inflate(R.layout.fragment_load, container, false)
    }

}