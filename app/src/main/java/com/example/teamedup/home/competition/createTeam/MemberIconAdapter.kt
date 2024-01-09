package com.example.teamedup.home.competition.createTeam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CompetitionItemBinding
import com.example.teamedup.databinding.MemberListIconItemBinding
import com.example.teamedup.repository.model.User
import com.example.teamedup.util.GlobalConstant
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class MemberIconAdapter : RecyclerView.Adapter<MemberIconAdapter.MemberIconViewHolder>() {
    private lateinit var context : Context
    inner class MemberIconViewHolder(val binding : MemberListIconItemBinding) : RecyclerView.ViewHolder(binding.root)
//    var memberList = ArrayList<User>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberIconAdapter.MemberIconViewHolder {
        context = parent.context
        return MemberIconViewHolder(MemberListIconItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    private var memberList = ArrayList<User>()

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: MemberIconAdapter.MemberIconViewHolder, position: Int) {
        val memberIcon = memberList[position]
        holder.binding.apply {
            if(memberIcon.picture.isNullOrBlank()){ }else{
                Picasso.with(context)
                    .load(memberIcon.picture)
                    .into(ivUserLogo)
            }
        }
    }

    fun setSelectedMemberList(user : User){
        memberList.add(user)
        notifyDataSetChanged()
    }

    fun setDeleteMemberList(user : User){
        memberList.remove(user)
        notifyDataSetChanged()
    }
}