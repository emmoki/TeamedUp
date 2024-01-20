package com.example.teamedup.home.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CompetitionItemBinding
import com.example.teamedup.databinding.TournamentHistoryListItemBinding
import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.model.Tournament
import com.squareup.picasso.Picasso

class UserTournamentHistoryAdapter : RecyclerView.Adapter<UserTournamentHistoryAdapter.UserTournamentHistoryViewHolder>() {
    private lateinit var context : Context
    inner class UserTournamentHistoryViewHolder(val binding : TournamentHistoryListItemBinding) : RecyclerView.ViewHolder(binding.root)
    private val diffCallback = object : DiffUtil.ItemCallback<Team>(){
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var teams : List<Team>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserTournamentHistoryAdapter.UserTournamentHistoryViewHolder {
        context = parent.context
        return UserTournamentHistoryViewHolder(TournamentHistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: UserTournamentHistoryAdapter.UserTournamentHistoryViewHolder,
        position: Int
    ) {
        val team = teams[position]
        holder.binding.apply {
            if(team.rank != null){
                tvTournamentRanking.text = setUpRankingSuffix(team.rank)
            }
            tvTournamentTier.text = team.tournament.tier
            Picasso.with(context)
                .load(team.tournament.icon)
                .into(ivTournamentGameIcon)
            tvTournamentName.text = team.tournament.name
        }
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    private fun setUpRankingSuffix(ranking : Int) : String{
        if(ranking == 1){
            return "1st"
        }else if(ranking == 2){
            return "2nd"
        }else if(ranking == 3){
            return "3rd"
        }
        return "3+"
    }
}