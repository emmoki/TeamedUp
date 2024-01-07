package com.example.teamedup.home.profile.claimablePrize

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentClaimablePrizeBinding

class ClaimablePrizeFragment : Fragment() {
    private lateinit var _binding : FragmentClaimablePrizeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentClaimablePrizeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupContent()
    }

    private fun setupToolbar(){
        binding.apply {
            toolbar.tvToolbarTitle.text = "Back"
            toolbar.btnCreate.visibility = View.GONE
            toolbar.back.setOnClickListener{
                findNavController().popBackStack()
            }
        }
    }

    private fun setupContent(){

    }
}