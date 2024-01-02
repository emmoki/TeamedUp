package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.model.User

data class JoinTournamentFormat(
    val statusCode : Int,
    val message : String,
    val data : FormatSuccessJoinTournament
)

