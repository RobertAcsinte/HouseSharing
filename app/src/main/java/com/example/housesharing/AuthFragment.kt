package com.example.housesharing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.housesharing.databinding.FragmentAuthBinding
import com.google.android.gms.common.SignInButton


class AuthFragment : Fragment() {

    lateinit var binding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_auth,
            container,
            false
        )

//        initSignInButton();
//        initAuthViewModel();
//        initGoogleSignInClient();


        return binding.root
    }

    private fun initSignInButton() {
        var googleSignInButton: SignInButton = binding.googleSignInButton
        googleSignInButton.setOnClickListener { signIn() }
    }

    private fun signIn() {
        TODO("Not yet implemented")
    }

}