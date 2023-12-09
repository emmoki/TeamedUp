package com.example.teamedup.home.participatedTournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamedup.databinding.FragmentParticipatedTournamentBinding


class ParticipatedTournamentFragment : Fragment() {
    private lateinit var _binding : FragmentParticipatedTournamentBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParticipatedTournamentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}