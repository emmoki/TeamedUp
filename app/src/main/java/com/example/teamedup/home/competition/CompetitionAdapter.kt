package com.example.teamedup.home.competition

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CompetitionItemBinding
import com.example.teamedup.databinding.GameListItemBinding
import com.example.teamedup.home.GameAdapter
import com.example.teamedup.repository.model.Game
import com.example.teamedup.repository.model.Tournament

class CompetitionAdapter : RecyclerView.Adapter<CompetitionAdapter.CompetitionViewHolder>() {
    inner class CompetitionViewHolder(val binding : CompetitionItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Tournament>(){
        override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var tournaments : List<Tournament>
        get() = differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        return CompetitionViewHolder(CompetitionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        holder.binding.apply {
            val tournament = tournaments[position]

//            ivCompetitionLogo = tournament.icon
            tvCompetitionName.text = tournament.name
//            tvCompetitionGame.text = tournament.game
            tvGroupJoined.text = tournament.totalParticipant.toString()
            tvGroupMax.text = tournament.maxParticipant.toString()
            tvCompetitionPrizePool.text = tournament.prize.toString()
        }
    }
}