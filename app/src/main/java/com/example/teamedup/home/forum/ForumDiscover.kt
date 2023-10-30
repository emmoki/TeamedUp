package com.example.teamedup.home.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamedup.databinding.FragmentForumDiscoverBinding

class ForumDiscover : Fragment() {
    private lateinit var _binding : FragmentForumDiscoverBinding
    private val binding get()  = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForumDiscoverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}