package com.example.teamedup.home.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.ForumListItemBinding
import com.example.teamedup.repository.model.Forum

class ForumAdapter : RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {
    inner class ForumViewHolder(val binding : ForumListItemBinding) : RecyclerView.ViewHolder(binding.root)
    lateinit var forums : List<Forum>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForumAdapter.ForumViewHolder {
        return ForumViewHolder(ForumListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ForumAdapter.ForumViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return forums.size
    }
}