package com.example.teamedup.home.forum.comment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CommentListItemBinding
import com.example.teamedup.repository.model.Comment
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CommentItemAdapter : RecyclerView.Adapter<CommentItemAdapter.CommentViewHolder>() {
    private lateinit var context : Context
    lateinit var gameID : String
    lateinit var forumID : String
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
        context = parent.context
        return CommentViewHolder(CommentListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.binding.apply {
            Picasso.with(context)
                .load(comment.user?.picture)
                .into(ivAuthorIcon)
            tvAuthorName.text = comment.user?.name

            tvCommentContent.text = comment.comment
            tvUpvote.text = comment.upVote.toString()
            tvDownVote.text = comment.downVote.toString()

            ivUpVote.setOnClickListener {
                comment.upVote++
                tvUpvote.text = comment.upVote.toString()
                updateCommentData(gameID, forumID, comment)
            }
            ivDownVote.setOnClickListener {
                comment.downVote++
                tvDownVote.text = comment.downVote.toString()
                updateCommentData(gameID, forumID, comment)
            }
        }
    }

    private fun updateCommentData(gameID : String, forumID : String, comment : Comment){
        CoroutineScope(Dispatchers.IO).launch {
//            binding.pbCompetitionDetail.isVisible = true
            val response = try {
                val updateForum = Comment (
                    comment = comment.comment,
                    upVote = comment.upVote,
                    downVote = comment.downVote,
                    id = null,
                    user = null
                )
                Log.d(TAG, "DataNeede: $gameID, $forumID, ${comment.id}, $updateForum")
                RetrofitInstances.api.updateComment(GlobalConstant.ATHENTICATION_TOKEN,gameID, forumID, comment.id!!,updateForum)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){

            }else{
                Log.d(TAG, "Response no successful")
                Log.d(TAG, "updateCommentData: ${response.message()}")
            }
//            binding.pbCompetitionDetail.isVisible = false
        }
    }


}