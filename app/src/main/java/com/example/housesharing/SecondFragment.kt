package com.example.housesharing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SecondFragment : Fragment() {
    lateinit var userOld: UserOld
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val bundle = arguments
        if (bundle == null) {
            Log.e("Confirmation", "Did not receive traveler information")
            return
        }

        // Retrieve passed arguments and display them
        val args = SecondFragmentArgs.fromBundle(bundle)
        userOld = args.userName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textViewSecond)
        textView.text = userOld.name
    }
}