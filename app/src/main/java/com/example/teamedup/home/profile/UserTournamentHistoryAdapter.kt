package com.example.teamedup.home.profile

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CompetitionItemBinding
import com.example.teamedup.databinding.TournamentHistoryListItemBinding

class UserTournamentHistoryAdapter : RecyclerView.Adapter<UserTournamentHistoryAdapter.UserTournamentHistoryViewHolder>() {
    inner class UserTournamentHistoryViewHolder(val binding : TournamentHistoryListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserTournamentHistoryAdapter.UserTournamentHistoryViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: UserTournamentHistoryAdapter.UserTournamentHistoryViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}