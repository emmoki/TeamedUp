package com.example.teamedup.home.forum.createForum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentCreateForumBinding


class CreateForumFragment : Fragment() {
    private lateinit var _binding : FragmentCreateForumBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateForumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOtherView()
    }

    private fun setUpOtherView(){
        binding.apply {
            toolbar.tvToolbarTitle.text = "Create Forum"
            toolbar.btnCreate.text = "Post"
        }
    }
}