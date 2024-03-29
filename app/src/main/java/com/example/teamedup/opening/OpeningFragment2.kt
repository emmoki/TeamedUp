package com.example.teamedup.opening

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentOpening1Binding
import com.example.teamedup.databinding.FragmentOpening2Binding

class OpeningFragment2 : Fragment() {
    private lateinit var _binding : FragmentOpening2Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpening2Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

}