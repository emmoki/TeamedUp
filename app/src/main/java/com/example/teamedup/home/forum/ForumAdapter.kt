package com.example.teamedup.home.forum

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.ForumListItemBinding
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.ForumRecyclerViewClickListener
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ForumAdapter : RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {
    private lateinit var context : Context
    lateinit var gameID : String
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
        context = parent.context
        return ForumViewHolder(ForumListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ForumAdapter.ForumViewHolder, position: Int) {
        val forum = forums[position]
        holder.binding.apply {
//            ivUserLogo.
            tvUserName.text = forum.user?.name

            when(forum.thumbnail.isNullOrEmpty()){
                true -> {ivForumThumbnail.visibility = View.GONE}
                false -> {
                    Log.d(TAG, "onBindViewHolder: ${forum.thumbnail}")
                    Picasso.with(context)
                        .load(forum.thumbnail)
                        .into(ivForumThumbnail)
                    ivForumThumbnail.visibility = View.VISIBLE
                }
            }
            tvForumTitle.text = forum.title
            tvForumDescription.text = forum.content
            tvUpvote.text = forum.upVote.toString()
            tvDownVote.text = forum.downVote.toString()

            ivUpVote.setOnClickListener {
                forum.upVote++
                tvUpvote.text = forum.upVote.toString()
                updateForumData(gameID, forum.id!!, forum)
            }
            ivDownVote.setOnClickListener {
                forum.downVote++
                tvDownVote.text = forum.downVote.toString()
                updateForumData(gameID, forum.id!!, forum)
            }
            forumItem.setOnClickListener {
                forumListener?.onItemClicked(it, forum)
            }
        }
    }

    override fun getItemCount(): Int {
        return forums.size
    }

    private fun updateForumData(gameID : String, forumID : String, forum : Forum){
        CoroutineScope(Dispatchers.IO).launch {
//            binding.pbCompetitionDetail.isVisible = true
            val response = try {
                val updateForum = Forum (
                    title = forum.title,
                    content = forum.content,
                    thumbnail = forum.thumbnail,
                    upVote = forum.upVote,
                    downVote = forum.downVote,
                    id = null,
                    comments = null,
                    user = null
                )
                RetrofitInstances.api.updateForums(GlobalConstant.ATHENTICATION_TOKEN,gameID, forumID, updateForum)
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
            }
//            binding.pbCompetitionDetail.isVisible = false
        }
    }
}