package com.example.teamedup.home.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentForumDiscoverBinding

class ForumDiscoverFragment : Fragment() {
    private lateinit var _binding : FragmentForumDiscoverBinding
    private val binding get()  = _binding
    private lateinit var forumAdapter : ForumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForumDiscoverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpForumRecyclerView()
    }

    private fun setUpForumRecyclerView(){
        binding.rvForumList.apply {
            forumAdapter = ForumAdapter()
            adapter = forumAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
        }
    }
}