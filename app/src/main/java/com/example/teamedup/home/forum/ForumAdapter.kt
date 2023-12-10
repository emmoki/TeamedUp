package com.example.teamedup.home.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.ForumListItemBinding
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.util.ForumRecyclerViewClickListener

class ForumAdapter : RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {
    inner class ForumViewHolder(val binding : ForumListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Forum>(){
        override fun areItemsTheSame(oldItem: Forum, newItem: Forum): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Forum, newItem: Forum): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var forums : List<Forum>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    var forumListener : ForumRecyclerViewClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForumAdapter.ForumViewHolder {
        return ForumViewHolder(ForumListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ForumAdapter.ForumViewHolder, position: Int) {
        val forum = forums[position]
        holder.binding.apply {
//            ivUserLogo
            tvUserName.text = forum.user?.name

            tvForumTitle.text = forum.title
            tvForumDescription.text = forum.content
            tvUpvote.text = forum.upVote.toString()
            tvDownVote.text = forum.downVote.toString()

            forumItem.setOnClickListener {
                forumListener?.onItemClicked(it, forum)
            }
        }
    }

    override fun getItemCount(): Int {
        return forums.size
    }
}