package com.example.teamedup.home.tournamentLog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.teamedup.R
import com.example.teamedup.databinding.TournamentLogItemBinding
import com.example.teamedup.repository.model.format.UpdateRankFormat

class TeamRankAdapter : RecyclerView.Adapter<TeamRankAdapter.TeamRankAdapterViewHolder>() {
    inner class TeamRankAdapterViewHolder(val binding : TournamentLogItemBinding) : RecyclerView.ViewHolder(binding.root)
    var teamList = listOf<String>("")
    private lateinit var rankItemAdapter : ArrayAdapter<Int>
    private lateinit var context : Context
    private var ranking = listOf<Int>()
    var rankingResult = ArrayList<UpdateRankFormat>()
    private lateinit var teamRank : Any

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamRankAdapterViewHolder {
        context = parent.context
        return TeamRankAdapterViewHolder(TournamentLogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: TeamRankAdapterViewHolder, position: Int) {
        val team = teamList[position]
        getRanking()

        holder.binding.apply {
            tvTeamName.text = team
            rankItemAdapter = ArrayAdapter(context, R.layout.dropdown_list_item, ranking.distinct())
            ctvTeamRanking.setAdapter(rankItemAdapter)
            ctvTeamRanking.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
                teamRank = adapterView.getItemAtPosition(i)
                rankingResult.add(UpdateRankFormat(team, teamRank.toString().toInt()))
            }
        }
    }

    private fun getRanking(){
        for (rank in 1..itemCount){
            ranking += rank
        }
    }
}