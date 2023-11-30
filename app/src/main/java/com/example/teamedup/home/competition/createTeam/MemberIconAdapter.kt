package com.example.teamedup.home.competition.createTeam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CompetitionItemBinding
import com.example.teamedup.databinding.MemberListIconItemBinding
import com.example.teamedup.repository.model.User

class MemberIconAdapter : RecyclerView.Adapter<MemberIconAdapter.MemberIconViewHolder>() {
    inner class MemberIconViewHolder(val binding : MemberListIconItemBinding) : RecyclerView.ViewHolder(binding.root)
    var memberList = ArrayList<User>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberIconAdapter.MemberIconViewHolder {
        return MemberIconViewHolder(MemberListIconItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    override fun onBindViewHolder(holder: MemberIconAdapter.MemberIconViewHolder, position: Int) {
        val memberIcon = memberList[position]
        holder.binding.apply {
        }
    }

}