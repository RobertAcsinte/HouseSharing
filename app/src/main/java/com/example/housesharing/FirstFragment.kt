package com.example.housesharing

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController


class FirstFragment : Fragment(), View.OnClickListener {

    var navController: NavController? = null
    lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.button).setOnClickListener(this)
        editText = view.findViewById(R.id.editTextTextPersonName)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.button -> {
                if(!TextUtils.isEmpty(editText.text.toString())){
                    val user = UserOld(editText.text.toString())
                    //val bundle = bundleOf("userName" to user)
                    //navController!!.navigate(R.id.action_firstFragment_to_secondFragment, bundle)
                    val directions = FirstFragmentDirections.actionFirstFragmentToSecondFragment(user)
                    findNavController().navigate(directions)
                }
            }
        }
    }

}