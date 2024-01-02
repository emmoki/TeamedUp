package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.model.User

data class FormatSuccessJoinTournament(
    val id : String? = "",
    val tournament : Tournament,
    val users : List<User>
)

