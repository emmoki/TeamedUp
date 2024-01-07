package com.example.teamedup.home.profile.participatedTournament

import androidx.lifecycle.ViewModel
import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.model.Tournament

class ParticipatedTournamentViewModel : ViewModel(){
    fun getTournamentFromTeam(teamList : List<Team>) : List<Tournament>{
        val tournament = ArrayList<Tournament>()
        for (team in teamList){
            tournament.add(team.tournament)
        }
        return tournament
    }
}