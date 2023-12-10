package com.example.teamedup.home.forum.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CommentListItemBinding
import com.example.teamedup.repository.model.Comment

class CommentItemAdapter : RecyclerView.Adapter<CommentItemAdapter.CommentViewHolder>() {
    inner class CommentViewHolder(val binding : CommentListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Comment>(){
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var comments : List<Comment>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(CommentListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.binding.apply {
//            ivAuthorIcon.drawable
//            tvAuthorName.text = comment.user.name

            tvCommentContent.text = comment.comment
            tvUpvote.text = comment.upVote.toString()
            tvDownVote.text = comment.downVote.toString()
        }
    }

}