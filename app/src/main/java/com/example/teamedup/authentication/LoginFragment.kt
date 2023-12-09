package com.example.teamedup.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var _binding : FragmentLoginBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOtherView()
    }

    private fun setupOtherView(){
        with(binding){
            toolbar.btnCreate.visibility = View.GONE
            toolbar.tvToolbarTitle.text = "Back"
            tvSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}