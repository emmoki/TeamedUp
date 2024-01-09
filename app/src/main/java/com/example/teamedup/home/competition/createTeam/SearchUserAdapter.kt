package com.example.teamedup.home.competition.createTeam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.teamedup.databinding.UserListItemBinding
import com.example.teamedup.repository.model.Game
import com.example.teamedup.repository.model.User
import com.example.teamedup.util.UserRecyclerViewClickListener
import com.squareup.picasso.Picasso

class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder>() {
    private lateinit var context : Context
    inner class SearchUserViewHolder(val binding : UserListItemBinding) : ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var users : List<User>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    var searchedUserListener : UserRecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        context = parent.context
        return SearchUserViewHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.binding.apply {
            val user = users[position]
            if(user.picture.isNullOrBlank()){ }else{
                Picasso.with(context)
                    .load(user.picture)
                    .into(ivUserLogo)
            }

            tvUserName.text = user.name
            itemUser.setOnClickListener {
                searchedUserListener?.onItemClicked(it, user )
            }
        }
    }

    fun setFilteredGameList(userList : List<User>){
        users = userList
        notifyDataSetChanged()
    }
}