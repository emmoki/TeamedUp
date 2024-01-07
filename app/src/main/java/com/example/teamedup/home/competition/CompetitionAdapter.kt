package com.example.teamedup.home.competition

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.databinding.CompetitionItemBinding
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.util.GameRecyclerViewClickListener
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TournamentRecyclerViewClickListener
import com.squareup.picasso.Picasso

class CompetitionAdapter : RecyclerView.Adapter<CompetitionAdapter.CompetitionViewHolder>() {
    private lateinit var context : Context
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

    var tournamentListener : TournamentRecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionViewHolder {
        context = parent.context
        return CompetitionViewHolder(CompetitionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        val tournament = tournaments[position]
        holder.binding.apply {
            if(tournament.icon != ""){
                Picasso.with(context)
                    .load(tournament.icon)
                    .into(ivCompetitionLogo)
            }
            tvCompetitionName.text = tournament.name
            tvCompetitionGame.text = tournament.game?.name
            tvGroupJoined.text = tournament.totalParticipant.toString()
            tvGroupMax.text = tournament.maxParticipant.toString()
            tvCompetitionPrizePool.text = tournament.prize.toString()
            competitionItem.setOnClickListener {
                tournamentListener?.onItemClicked(it,tournament)
            }

            if(tournament.host == GlobalConstant.user){

            }
        }
    }
}