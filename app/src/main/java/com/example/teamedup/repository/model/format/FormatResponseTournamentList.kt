package com.example.teamedup.repository.model.format

import com.example.teamedup.repository.model.Tournament

data class FormatResponseTournamentList(
    val statusCode : Int,
    val message : String,
    val data : List<Tournament>
)
