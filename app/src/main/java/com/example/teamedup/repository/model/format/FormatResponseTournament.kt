package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Game
import com.example.teamedup.repository.model.Tournament

data class FormatResponseTournament(
    val statusCode : Int,
    val message : String,
    val data : Tournament
)
