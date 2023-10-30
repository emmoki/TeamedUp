package com.example.teamedup.home.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamedup.databinding.FragmentCompetionDiscoverBinding


class CompetitionDiscover : Fragment() {
    private lateinit var _binding : FragmentCompetionDiscoverBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompetionDiscoverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}