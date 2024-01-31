package com.example.teamedup.home.tournament.createTeam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.MemberListNameItemBinding
import com.example.teamedup.repository.model.User
import com.example.teamedup.util.UserDeleteRecyclerViewClickListener

class MemberNameAdapter : RecyclerView.Adapter<MemberNameAdapter.MemberNameViewHolder>() {
    private lateinit var context : Context
    inner class MemberNameViewHolder(val binding : MemberListNameItemBinding) : RecyclerView.ViewHolder(binding.root)
    var memberList = ArrayList<User>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberNameAdapter.MemberNameViewHolder {
        context = parent.context
        return MemberNameViewHolder(MemberListNameItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    var deleteUserListener : UserDeleteRecyclerViewClickListener? = null

    override fun onBindViewHolder(holder: MemberNameAdapter.MemberNameViewHolder, position: Int) {
        val member = memberList[position]
        holder.binding.apply {
            tvMemberName.text = member.name
            ivDeleteMember.setOnClickListener {
                deleteUserListener?.onUserDeleteClicked(it, member)
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