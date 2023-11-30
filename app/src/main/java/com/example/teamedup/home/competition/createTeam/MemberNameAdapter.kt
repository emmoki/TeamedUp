package com.example.teamedup.home.competition.createTeam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.MemberListNameItemBinding
import com.example.teamedup.repository.model.User

class MemberNameAdapter : RecyclerView.Adapter<MemberNameAdapter.MemberNameViewHolder>() {
    inner class MemberNameViewHolder(val binding : MemberListNameItemBinding) : RecyclerView.ViewHolder(binding.root)
    var memberList = ArrayList<User>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberNameAdapter.MemberNameViewHolder {
        return MemberNameViewHolder(MemberListNameItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: MemberNameAdapter.MemberNameViewHolder, position: Int) {
        val member = memberList[position]
        holder.binding.apply {
            tvMemberName.text = member.name
        }
    }

}